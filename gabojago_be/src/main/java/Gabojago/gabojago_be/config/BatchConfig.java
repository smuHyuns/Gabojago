package Gabojago.gabojago_be.config;

import Gabojago.gabojago_be.entity.ExchangeRateTemp;
import Gabojago.gabojago_be.entity.Trip;
import Gabojago.gabojago_be.exception.ErrorCode;
import Gabojago.gabojago_be.exception.GabojagoException;
import Gabojago.gabojago_be.exchangeRate.CurrencyCountryMapping;
import Gabojago.gabojago_be.exchangeRate.ExchangeRateRepository;
import Gabojago.gabojago_be.exchangeRate.ExchangeRateService;
import Gabojago.gabojago_be.exchangeRate.ExchangeRateTempRepository;
import Gabojago.gabojago_be.trip.TripRepository;
import Gabojago.gabojago_be.trip.TripService;
import Gabojago.gabojago_be.trip.TripUtilService;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.ChunkListener;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.*;

@Configuration
@EnableBatchProcessing
@Slf4j
public class BatchConfig {

    private static final int CHUNK_SIZE = 100;
    private final ExchangeRateService exchangeRateService;
    private final ExchangeRateTempRepository exchangeRateTempRepository;
    private final TripRepository tripRepository;
    private final JobRepository jobRepository;

    public BatchConfig(final ExchangeRateService exchangeRateService, ExchangeRateTempRepository exchangeRateTempRepository,
                       TripRepository tripRepository, JobRepository jobRepository) {
        this.exchangeRateService = exchangeRateService;
        this.exchangeRateTempRepository = exchangeRateTempRepository;
        this.tripRepository = tripRepository;
        this.jobRepository = jobRepository;
    }

    @Bean
    public ItemReader<Map.Entry<String, Object>> exchangeRateReader() {
        return new ItemReader<>() {
            private Map<String, Object> rates;
            private Iterator<Map.Entry<String, Object>> iterator;

            @Override
            public Map.Entry<String, Object> read() {
                if (rates == null) {
                    rates = exchangeRateService.getAllExchangeRates();
                    iterator = rates.entrySet().iterator();
                }

                return iterator.hasNext() ? iterator.next() : null;
            }
        };
    }


    @Bean
    public ItemProcessor<Map.Entry<String, Object>, ExchangeRateTemp> exchangeRateProcessor() {
        return entry -> {
            String currency = entry.getKey();
            Object value = entry.getValue();

            if (!(value instanceof Number)) {
                throw new GabojagoException(ErrorCode.EXCHANGE_RATE_INVALID_FORMAT);
            }

            ExchangeRateTemp temp = new ExchangeRateTemp();
            temp.setCurrency(currency);
            temp.setRate(((Number) value).doubleValue());
            temp.setCountry(CurrencyCountryMapping.getMapping().getOrDefault(currency, "Unknown"));
            return temp;
        };
    }


    @Bean
    public ItemWriter<ExchangeRateTemp> exchangeRateWriter() {
        return items -> {
            // 기존 데이터와 비교해 ID 설정 후 저장
            for (ExchangeRateTemp temp : items) {
                ExchangeRateTemp existingRate = exchangeRateTempRepository.findByCountryAndCurrency(
                        temp.getCountry(), temp.getCurrency()
                );
                if (existingRate != null) {
                    temp.setExchangeRateId(existingRate.getExchangeRateId());
                }
            }
            exchangeRateTempRepository.saveAll(items);
        };
    }

    @Bean
    public Step exchangeRateStep(JobRepository jobRepository,
                                 PlatformTransactionManager transactionManager,
                                 ItemReader<Map.Entry<String, Object>> reader,
                                 ItemProcessor<Map.Entry<String, Object>, ExchangeRateTemp> processor,
                                 ItemWriter<ExchangeRateTemp> writer) {
        return new StepBuilder("exchangeRateStep", jobRepository)
                .<Map.Entry<String, Object>, ExchangeRateTemp>chunk(100, transactionManager)
                .reader(reader)
                .processor(processor)
                .writer(writer)
                .build();
    }

    @Bean
    public Job exchangeRateJob(JobRepository jobRepository,
                               @Qualifier("exchangeRateStep") Step exchangeRateStep) {
        return new JobBuilder("exchangeRateJob", jobRepository)
                .start(exchangeRateStep)
                .build();
    }

    @Bean
    public ItemReader<Trip> tripReader() {
        return new ItemReader<>() {
            private int page = 0;
            private List<Trip> currentTrips = new ArrayList<>();
            private int currentIndex = 0;

            @Override
            public Trip read() {
                if (currentIndex < currentTrips.size()) {
                    return currentTrips.get(currentIndex++);
                }

                Pageable pageable = PageRequest.of(page, CHUNK_SIZE);
                currentTrips = tripRepository.findAll(pageable).getContent();
                currentIndex = 0;
                page++;

                if (currentTrips.isEmpty()) {
                    return null;
                }

                log.info("Loaded {} trips for page {}", currentTrips.size(), page - 1);
                return currentTrips.get(currentIndex++);
            }
        };
    }

    @Bean
    @StepScope
    public ItemProcessor<Trip, Trip> tripProcessor(TripUtilService tripUtilService) {
        return trip -> {
            try {
                int oldStatus = trip.getTripStatus();
                int newStatus = tripUtilService.calculateTripStatus(trip.getStartPeriod(), trip.getEndPeriod());
                trip.setTripStatus(newStatus);
                log.info("Trip ID: {}, Old Status: {}, New Status: {}",
                        trip.getTripId(), oldStatus, trip.getTripStatus());
                return trip;
            } catch (Exception e) {
                log.error("Error processing Trip ID: {}", trip.getTripId(), e);
                return trip;
            }
        };
    }

    @Bean
    @Transactional
    public ItemWriter<Trip> tripWriter() {
        return chunk -> {
            if (chunk.isEmpty()) {
                log.warn("청크에 값이 존재하지 않습니다.");
                return;
            }
            List<Trip> trips = (List<Trip>) chunk.getItems();
            tripRepository.saveAll(trips);
            log.info("저장한 Trip 수 : {}", trips.size());
        };
    }

    @Bean
    public Step tripUpdateStep(JobRepository jobRepository,
                               PlatformTransactionManager transactionManager,
                               ItemReader<Trip> tripReader,
                               ItemProcessor<Trip, Trip> tripProcessor,
                               ItemWriter<Trip> tripWriter) {
        return new StepBuilder("tripUpdateStep", jobRepository)
                .<Trip, Trip>chunk(CHUNK_SIZE, transactionManager)
                .reader(tripReader)
                .processor(tripProcessor)
                .writer(tripWriter)
                .transactionManager(transactionManager)  // 명시적으로 트랜잭션 매니저 설정
                .listener(new ChunkListener() {
                    @Override
                    public void beforeChunk(ChunkContext context) {
                        log.info("Starting chunk processing 진행중 ...");
                    }

                    @Override
                    public void afterChunk(ChunkContext context) {
                        log.info("Chunk processing 완료");
                    }

                    @Override
                    public void afterChunkError(ChunkContext context) {
                        log.error("chunk processing 중 에러 발생");
                    }
                })
                .build();
    }

    @Bean
    public Job tripUpdateJob(JobRepository jobRepository, @Qualifier("tripUpdateStep") Step tripUpdateStep) {
        return new JobBuilder("tripUpdateJob", jobRepository)
                .start(tripUpdateStep)
                .build();
    }

}

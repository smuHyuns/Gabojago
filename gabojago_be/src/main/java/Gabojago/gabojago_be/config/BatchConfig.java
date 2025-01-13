package Gabojago.gabojago_be.config;

import Gabojago.gabojago_be.entity.ExchangeRateTemp;
import Gabojago.gabojago_be.entity.Trip;
import Gabojago.gabojago_be.exception.ErrorCode;
import Gabojago.gabojago_be.exception.GabojagoException;
import Gabojago.gabojago_be.exchangeRate.CurrencyCountryMapping;
import Gabojago.gabojago_be.exchangeRate.ExchangeRateService;
import Gabojago.gabojago_be.exchangeRate.ExchangeRateTempRepository;
import Gabojago.gabojago_be.trip.TripRepository;
import Gabojago.gabojago_be.trip.TripService;
import Gabojago.gabojago_be.trip.TripUtilService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
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
@RequiredArgsConstructor
@EnableBatchProcessing
@Slf4j
public class BatchConfig {

    private static final int CHUNK_SIZE = 100;
    private final ExchangeRateService exchangeRateService;
    private final ExchangeRateTempRepository exchangeRateTempRepository;
    private final TripRepository tripRepository;
    private final JobRepository jobRepository;

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
                .<Map.Entry<String, Object>, ExchangeRateTemp>chunk(10, transactionManager)
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
                // 현재 리스트에서 데이터를 읽음
                if (currentIndex < currentTrips.size()) {
                    return currentTrips.get(currentIndex++);
                }

                // 새로운 페이지 데이터를 로드
                Pageable pageable = PageRequest.of(page, CHUNK_SIZE);
                currentTrips = tripRepository.findAll(pageable).getContent();
                currentIndex = 0;
                page++;

                // 데이터가 더 이상 없으면 null 반환
                if (currentTrips.isEmpty()) {
                    return null;
                }

                return currentTrips.get(currentIndex++);
            }
        };
    }


    @Bean
    public ItemProcessor<Trip, Trip> tripProcessor(TripUtilService tripUtilService) {
        return trip -> {
            try {
                int newStatus = tripUtilService.calculateTripStatus(trip.getStartPeriod(), trip.getEndPeriod());
                trip.setTripStatus(newStatus);
                log.info("처리된 Trip 데이터 상태: {}", trip.getTripStatus());
                return trip;
            } catch (Exception e) {
                log.error("Trip 상태 계산 중 오류 발생: {}", trip, e);
                throw e;
            }
        };
    }


    @Bean
    public ItemWriter<Trip> tripWriter(TripService tripService) {
        return trips -> {
            for (Trip trip : trips) {
                log.info("tripID : {} / tripStatus : {}", trip.getTripId(), trip.getTripStatus());
                tripService.saveTrips(trip);
            }

            if (trips.isEmpty()) {
                log.warn("저장할 Trip 데이터가 비어 있습니다.");
                return;
            }
            log.info("{}개의 Trip 데이터를 저장했습니다.", trips.size());
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
                .build();
    }

    @Bean
    public Job tripUpdateJob(JobRepository jobRepository, @Qualifier("tripUpdateStep") Step tripUpdateStep) {
        return new JobBuilder("tripUpdateJob", jobRepository)
                .start(tripUpdateStep)
                .build();
    }

}

package Gabojago.gabojago_be.config;

import Gabojago.gabojago_be.entity.ExchangeRateTemp;
import Gabojago.gabojago_be.entity.Trip;
import Gabojago.gabojago_be.exception.ErrorCode;
import Gabojago.gabojago_be.exception.GabojagoException;
import Gabojago.gabojago_be.exchangeRate.CurrencyCountryMapping;
import Gabojago.gabojago_be.exchangeRate.ExchangeRateService;
import Gabojago.gabojago_be.exchangeRate.ExchangeRateTempRepository;
import Gabojago.gabojago_be.trip.TripRepository;
import jakarta.persistence.Column;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.repository.support.JobRepositoryFactoryBean;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;

import org.springframework.batch.item.database.JpaPagingItemReader;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


import org.springframework.transaction.PlatformTransactionManager;


import javax.sql.DataSource;
import java.time.LocalDate;
import java.util.*;

@Configuration
@EnableBatchProcessing
@Slf4j
public class BatchConfig {
    private final EntityManager entityManager;
    private final EntityManagerFactory entityManagerFactory;
    private final ExchangeRateService exchangeRateService;
    private final ExchangeRateTempRepository exchangeRateTempRepository;

    private final TripRepository tripRepository;

    public BatchConfig(ExchangeRateService exchangeRateService, ExchangeRateTempRepository exchangeRateTempRepository,
                       TripRepository tripRepository, EntityManagerFactory entityManagerFactory, EntityManager entityManager) {
        this.exchangeRateService = exchangeRateService;
        this.exchangeRateTempRepository = exchangeRateTempRepository;
        this.tripRepository = tripRepository;
        this.entityManagerFactory = entityManagerFactory;
        this.entityManager = entityManager;
    }


    @Bean
    public JobRepository jobRepository(DataSource dataSource, PlatformTransactionManager transactionManager) throws Exception {
        JobRepositoryFactoryBean factoryBean = new JobRepositoryFactoryBean();
        factoryBean.setDataSource(dataSource);
        factoryBean.setTransactionManager(transactionManager);
        factoryBean.setIsolationLevelForCreate("ISOLATION_READ_COMMITTED");
        factoryBean.setTablePrefix("BATCH_");
        factoryBean.afterPropertiesSet();
        return factoryBean.getObject();
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
    public Job updateTripStatusJob(JobRepository jobRepository,
                                   @Qualifier("updateTripStatusStep") Step updateTripStatusStep) {
        return new JobBuilder("updateTripStatusJob", jobRepository)
                .start(updateTripStatusStep)
                .incrementer(new RunIdIncrementer())
                .build();
    }


    @Bean
    public Step updateTripStatusStep(JobRepository jobRepository,
                                     PlatformTransactionManager transactionManager,
                                     JpaPagingItemReader<Trip> tripReader,
                                     ItemProcessor<Trip, Trip> tripProcessor,
                                     ItemWriter<Trip> tripWriter) {
        return new StepBuilder("updateTripStatusStep", jobRepository)
                .<Trip, Trip>chunk(100, transactionManager)
                .reader(tripReader)
                .processor(tripProcessor)
                .writer(tripWriter)
                .build();
    }


    @Bean
    public JpaPagingItemReader<Trip> tripReader() {
        JpaPagingItemReader<Trip> reader = new JpaPagingItemReader<>();
        reader.setQueryString("SELECT t FROM Trip t ORDER BY t.tripId ASC");
        reader.setEntityManagerFactory(entityManagerFactory);
        reader.setPageSize(100);
        return reader;
    }


    @Bean
    public ItemProcessor<Trip, Trip> tripProcessor(EntityManager entityManager) {
        return trip -> {
            LocalDate today = LocalDate.now();

            int newStatus = trip.getEndPeriod().isBefore(today) ? 2 :
                    (!trip.getStartPeriod().isAfter(today)) ? 1 : 0;

            if (trip.getTripStatus() != newStatus) {
                log.info("Trip ID {}: Status updated from {} to {}", trip.getTripId(), trip.getTripStatus(), newStatus);
                trip.setTripStatus(newStatus);
                return entityManager.merge(trip);
            }
            return null;
        };
    }

    @Bean
    public ItemWriter<Trip> tripWriter(EntityManagerFactory entityManagerFactory) {
        return items -> {
            EntityManager entityManager = entityManagerFactory.createEntityManager();
            entityManager.getTransaction().begin();
            try {
                for (Trip trip : items) {
                    entityManager.merge(trip);
                }
                entityManager.getTransaction().commit();
            } catch (Exception e) {
                entityManager.getTransaction().rollback();
                throw e;
            } finally {
                entityManager.close();
            }
        };
    }
}

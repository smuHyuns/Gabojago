package Gabojago.gabojago_be.config;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
@EnableBatchProcessing
public class BatchConfig {

    @Bean
    public Job transactionUpdateJob(JobRepository jobRepository, Step transactionStep) {
        return new JobBuilder("transactionUpdateJob", jobRepository)
                .start(transactionStep)
                .build();
    }

    @Bean
    public Step transactionStep(JobRepository jobRepository,
                                PlatformTransactionManager transactionManager,
                                ItemReader<String> reader,
                                ItemProcessor<String, String> processor,
                                ItemWriter<String> writer) {
        return new StepBuilder("transactionStep", jobRepository)
                .<String, String>chunk(10, transactionManager)
                .reader(reader)
                .processor(processor)
                .writer(writer)
                .build();
    }

    @Bean
    public ItemReader<String> reader() {
        return () -> "Sample Data";
    }

    @Bean
    public ItemProcessor<String, String> processor() {
        return item -> item.toUpperCase();
    }

    @Bean
    public ItemWriter<String> writer() {
        return items -> items.forEach(System.out::println);
    }
}

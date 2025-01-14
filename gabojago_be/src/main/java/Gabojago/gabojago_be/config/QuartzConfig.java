package Gabojago.gabojago_be.config;


import Gabojago.gabojago_be.scheduler.ExchangeJobJobLauncher;
import Gabojago.gabojago_be.scheduler.TripStatusJobLauncher;
import org.quartz.*;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.TimeZone;

@Configuration
public class QuartzConfig {

    @Bean
    public TimeZone krTimeZone() {
        return TimeZone.getTimeZone("Asia/Seoul");
    }

    @Bean
    public JobDetail exchangeRateJobDetail() {
        return JobBuilder.newJob(ExchangeJobJobLauncher.class)
                .withIdentity("exchangeRateJobDetail")
                .storeDurably()
                .build();
    }

    @Bean
    public Trigger exchangeRateJobTrigger(@Qualifier("exchangeRateJobDetail") JobDetail exchangeRateJobDetail) {
        return TriggerBuilder.newTrigger()
                .forJob(exchangeRateJobDetail)
                .withIdentity("exchangeRateJobTrigger")
                .withSchedule(CronScheduleBuilder.cronSchedule("0 0 6 * * ?")
                        .inTimeZone(krTimeZone()))
                .build();
    }

    @Bean
    public JobDetail tripUpdateJobDetail() {
        return JobBuilder.newJob(TripStatusJobLauncher.class)
                .withIdentity("tripUpdateJobDetail")
                .storeDurably()
                .build();
    }

    @Bean
    public Trigger tripUpdateJobTrigger(@Qualifier("tripUpdateJobDetail") JobDetail tripUpdateJobDetail) {
        return TriggerBuilder.newTrigger()
                .forJob(tripUpdateJobDetail)
                .withIdentity("tripUpdateJobTrigger")
                .withSchedule(CronScheduleBuilder.cronSchedule("0 * * * * ?")
                        .inTimeZone(krTimeZone()))
                .build();
    }
}

package Gabojago.gabojago_be.config;

import Gabojago.gabojago_be.scheduler.jobs.SampleJob;
import org.quartz.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class QuartzConfig {
    @Bean
    public JobDetail jobDetail() {
        return JobBuilder.newJob(SampleJob.class)
                .withIdentity("sampleJob")
                .storeDurably()
                .build();
    }

    @Bean
    public Trigger trigger(JobDetail jobDetail) {
        return TriggerBuilder.newTrigger()
                .forJob(jobDetail)
                .withIdentity("sampleTrigger")
                .withSchedule(CronScheduleBuilder.cronSchedule("0 0/1 * 1/1 * ? *")) // 매 분 실행
                .build();
    }
}

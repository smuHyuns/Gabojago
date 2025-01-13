package Gabojago.gabojago_be.scheduler;

import lombok.extern.slf4j.Slf4j;
import org.quartz.JobExecutionContext;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class TripStatusJobLauncher extends QuartzJobBean {

    private final JobLauncher jobLauncher;
    private final Job tripUpdateJob;

    public TripStatusJobLauncher(JobLauncher jobLauncher,
                                 @Qualifier("tripUpdateJob") Job tripUpdateJob) {
        this.jobLauncher = jobLauncher;
        this.tripUpdateJob = tripUpdateJob;
    }

    @Override
    protected void executeInternal(JobExecutionContext context) {
        try {
            JobParameters jobParameters = new JobParametersBuilder()
                    .addLong("timestamp", System.currentTimeMillis())
                    .toJobParameters();
            log.info("Quartz Trigger 실행: tripUpdateJob 시작");
            jobLauncher.run(tripUpdateJob, jobParameters);
            log.info("Quartz Trigger 완료: tripUpdateJob 실행 완료");
        } catch (Exception e) {
            log.error("batch Job 실행 실패 : {}", e.getMessage());
        }
    }
}

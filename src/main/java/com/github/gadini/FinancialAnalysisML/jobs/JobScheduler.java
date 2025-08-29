package com.github.gadini.FinancialAnalysisML.jobs;

import com.github.gadini.FinancialAnalysisML.config.BatchJobConfig;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class JobScheduler {

    private final BatchJobConfig batchJobConfig;
    private final JobLauncher jobLauncher;

    @Qualifier("insertMetricasFinanceirasJob")
    private final Job insertMetricasFinanceirasJob;

    @Scheduled(cron = "${financial.analysis.job.insertMetricasFinanceiras.cron}")
    public void runInsertMetricasFinanceirasJob() throws JobInstanceAlreadyCompleteException, JobExecutionAlreadyRunningException, JobParametersInvalidException, JobRestartException {
        if(batchJobConfig.getInsertMetricasFinanceiras().isEnable()){
            JobParameters params = new JobParametersBuilder()
                    .addLong("time", System.currentTimeMillis())
                    .toJobParameters();
            jobLauncher.run(insertMetricasFinanceirasJob, params);
        }
    }
}

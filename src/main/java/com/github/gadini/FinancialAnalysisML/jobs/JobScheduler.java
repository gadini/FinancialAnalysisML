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

    @Qualifier("insertClassificacaoJob")
    private final Job insertClassificacaoJob;

    @Qualifier("insertRedeArestasJob")
    private final Job insertRedeArestasJob;

    @Qualifier("insertRedeMetricasJob")
    private final Job insertRedeMetricasJob;

    @Scheduled(cron = "${financial.analysis.job.insertMetricasFinanceiras.cron}")
    public void runInsertMetricasFinanceirasJob() throws JobInstanceAlreadyCompleteException, JobExecutionAlreadyRunningException, JobParametersInvalidException, JobRestartException {
        if(batchJobConfig.getInsertMetricasFinanceiras().isEnable()){
            JobParameters params = new JobParametersBuilder()
                    .addLong("time", System.currentTimeMillis())
                    .toJobParameters();
            jobLauncher.run(insertMetricasFinanceirasJob, params);
        }
    }

    @Scheduled(cron = "${financial.analysis.job.insertClassificacao.cron}")
    public void runInsertClassificacaoJob() throws JobInstanceAlreadyCompleteException, JobExecutionAlreadyRunningException, JobParametersInvalidException, JobRestartException {
        if(batchJobConfig.getInsertClassificacao().isEnable()){
            JobParameters params = new JobParametersBuilder()
                    .addLong("time", System.currentTimeMillis())
                    .toJobParameters();
            jobLauncher.run(insertClassificacaoJob, params);
        }
    }

    @Scheduled(cron = "${financial.analysis.job.insertRedeArestas.cron}")
    public void runInsertRedeArestasJob() throws JobInstanceAlreadyCompleteException, JobExecutionAlreadyRunningException, JobParametersInvalidException, JobRestartException {
        if(batchJobConfig.getInsertRedeArestas().isEnable()){
            JobParameters params = new JobParametersBuilder()
                    .addLong("time", System.currentTimeMillis())
                    .toJobParameters();
            jobLauncher.run(insertRedeArestasJob, params);
        }
    }

    @Scheduled(cron = "${financial.analysis.job.insertRedeMetricas.cron}")
    public void runInsertRedeMetricasJob() throws JobInstanceAlreadyCompleteException, JobExecutionAlreadyRunningException, JobParametersInvalidException, JobRestartException {
        if(batchJobConfig.getInsertRedeMetricas().isEnable()){
            JobParameters params = new JobParametersBuilder()
                    .addLong("time", System.currentTimeMillis())
                    .toJobParameters();
            jobLauncher.run(insertRedeMetricasJob, params);
        }
    }
}

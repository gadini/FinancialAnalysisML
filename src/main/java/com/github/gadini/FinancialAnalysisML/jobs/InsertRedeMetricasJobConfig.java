package com.github.gadini.FinancialAnalysisML.jobs;

import com.github.gadini.FinancialAnalysisML.tasklets.InsertRedeArestasTasklet;
import com.github.gadini.FinancialAnalysisML.tasklets.InsertRedeMetricasTasklet;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
@RequiredArgsConstructor
public class InsertRedeMetricasJobConfig {

    private final JobRepository jobRepository;
    private final PlatformTransactionManager transactionManager;
    private final InsertRedeMetricasTasklet tasklet;

    @Bean
    public Job insertRedeMetricasJob() {
        Step step = insertRedeMetricasStep();
        return new JobBuilder("insertRedeMetricasJob", jobRepository)
                .incrementer(new RunIdIncrementer())
                .preventRestart()
                .start(step)
                .build();
    }

    @Bean
    public Step insertRedeMetricasStep() {
        return new StepBuilder("insertRedeMetricasStep", jobRepository)
                .tasklet(tasklet, transactionManager)
                .build();
    }

}

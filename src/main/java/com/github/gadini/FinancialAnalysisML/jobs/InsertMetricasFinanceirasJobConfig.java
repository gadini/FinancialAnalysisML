package com.github.gadini.FinancialAnalysisML.jobs;

import com.github.gadini.FinancialAnalysisML.tasklets.InsertMetricasFinanceirasTasklet;
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

import java.util.Collections;

@Configuration
@RequiredArgsConstructor
public class InsertMetricasFinanceirasJobConfig {

    private final JobRepository jobRepository;
    private final PlatformTransactionManager transactionManager;
    private final InsertMetricasFinanceirasTasklet tasklet;

    @Bean
    public Job insertMetricasFinanceirasJob() {
        Step step = insertMetricasFinanceirasStep();
        return new JobBuilder("insertMetricasFinanceirasJob", jobRepository)
                .incrementer(new RunIdIncrementer())
                .preventRestart()
                .start(step)
                .build();
    }

    @Bean
    public Step insertMetricasFinanceirasStep() {
        return new StepBuilder("insertMetricasFinanceirasStep", jobRepository)
                .tasklet(tasklet, transactionManager)
                .build();
    }
}

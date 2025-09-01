package com.github.gadini.FinancialAnalysisML.jobs;

import com.github.gadini.FinancialAnalysisML.tasklets.InsertClassificacaoTasklet;
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
public class InsertClassificacaoJobConfig {

    private final JobRepository jobRepository;
    private final PlatformTransactionManager transactionManager;
    private final InsertClassificacaoTasklet tasklet;

    @Bean
    public Job insertClassificacaoJob() {
        Step step = insertClassificacaoStep();
        return new JobBuilder("insertClassificacaoJob", jobRepository)
                .incrementer(new RunIdIncrementer())
                .preventRestart()
                .start(step)
                .build();
    }

    @Bean
    public Step insertClassificacaoStep() {
        return new StepBuilder("insertClassificacaoStep", jobRepository)
                .tasklet(tasklet, transactionManager)
                .build();
    }
}

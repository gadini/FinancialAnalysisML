package com.github.gadini.FinancialAnalysisML.tasklets;

import com.github.gadini.FinancialAnalysisML.service.MetricasFinanceirasService;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class InsertMetricasFinanceirasTasklet implements Tasklet {

    @Autowired
    private MetricasFinanceirasService metricasFinanceirasService;

    @Override
    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
        metricasFinanceirasService.calcularMetricasFinanceiras();
        return RepeatStatus.FINISHED;
    }
}

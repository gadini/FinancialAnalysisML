package com.github.gadini.FinancialAnalysisML.tasklets;

import com.github.gadini.FinancialAnalysisML.service.MetricasFinanceirasService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Slf4j
@Component
public class InsertMetricasFinanceirasTasklet implements Tasklet {

    @Autowired
    private MetricasFinanceirasService metricasFinanceirasService;

    @Override
    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
        LocalDate inicio =  LocalDate.now().minusMonths(1).withDayOfMonth(1);
        LocalDate fim = inicio.withDayOfMonth(inicio.lengthOfMonth());
        metricasFinanceirasService.calcularMetricasFinanceiras(inicio, fim);
        log.info("DEU CERTO");
        return RepeatStatus.FINISHED;
    }
}

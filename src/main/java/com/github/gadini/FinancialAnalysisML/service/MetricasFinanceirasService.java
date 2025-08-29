package com.github.gadini.FinancialAnalysisML.service;

import com.github.gadini.FinancialAnalysisML.domain.entity.Empresa;
import com.github.gadini.FinancialAnalysisML.domain.entity.MetricasFinanceiras;
import com.github.gadini.FinancialAnalysisML.repository.MetricasFinanceirasRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.Chunk;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class MetricasFinanceirasService {

    @Autowired
    private MetricasFinanceirasRepository metricasFinanceirasRepository;

    public MetricasFinanceiras calcularMetricasFinanceiras(){
        log.info("TESTE JOB SUCESSO");
        MetricasFinanceiras metricasFinanceiras = new MetricasFinanceiras();
        return metricasFinanceiras;
    }

    public void salvarMetricasFinanceiras(Chunk<? extends MetricasFinanceiras> items){
        //metricasFinanceirasRepository.saveAll(items);
        log.info("teste");
    }
}

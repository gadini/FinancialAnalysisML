package com.github.gadini.FinancialAnalysisML.service;

import com.github.gadini.FinancialAnalysisML.repository.MetricasFinanceirasRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Slf4j
@Service
public class MetricasFinanceirasService {

    @Autowired
    private MetricasFinanceirasRepository metricasFinanceirasRepository;

    @Transactional
    public void calcularMetricasFinanceiras(LocalDate inicio, LocalDate fim){
        metricasFinanceirasRepository.spCalcularMetricasPorPeriodo(inicio, fim);
    }
}

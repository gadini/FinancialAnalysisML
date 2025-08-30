package com.github.gadini.FinancialAnalysisML.service;

import com.github.gadini.FinancialAnalysisML.domain.mapper.MetricasFinanceirasMapper;
import com.github.gadini.FinancialAnalysisML.domain.request.PeriodoRequest;
import com.github.gadini.FinancialAnalysisML.domain.response.ListAllEmpresaRelacaoResponse;
import com.github.gadini.FinancialAnalysisML.domain.response.ListEmpresaRelacaoByOrigemResponse;
import com.github.gadini.FinancialAnalysisML.domain.response.MetricasFinanceirasResponse;
import com.github.gadini.FinancialAnalysisML.repository.MetricasFinanceirasRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedModel;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Slf4j
@Service
public class MetricasFinanceirasService {

    @Autowired
    private MetricasFinanceirasMapper metricasFinanceirasMapper;

    @Autowired
    private MetricasFinanceirasRepository metricasFinanceirasRepository;

    public PagedModel<MetricasFinanceirasResponse> listarMetricasFinanceiras(Pageable pageable){
        return new PagedModel<> (metricasFinanceirasRepository.findAll(pageable).map(metricasFinanceirasMapper::toResponse));
    }

    public PagedModel<MetricasFinanceirasResponse> listarMetricasPorEmpresa(Long empresaId, Pageable pageable) {
        return new PagedModel<>(metricasFinanceirasRepository.findByEmpresaId(empresaId, pageable).map(metricasFinanceirasMapper::toResponse));
    }

    public PagedModel<MetricasFinanceirasResponse> listarMetricasPorPeriodo(PeriodoRequest request, Pageable pageable) {
        return new PagedModel<>(metricasFinanceirasRepository.findByPeriodoInicioAndPeriodoFim(request.getInicio(), request.getFim(), pageable).map(metricasFinanceirasMapper::toResponse));
    }

    @Transactional
    public void calcularMetricasFinanceiras(LocalDate inicio, LocalDate fim){
        metricasFinanceirasRepository.spCalcularMetricasPorPeriodo(inicio, fim);
    }
}

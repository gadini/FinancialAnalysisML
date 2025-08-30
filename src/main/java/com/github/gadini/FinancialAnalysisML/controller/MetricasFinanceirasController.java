package com.github.gadini.FinancialAnalysisML.controller;

import com.github.gadini.FinancialAnalysisML.domain.response.MetricasFinanceirasResponse;
import com.github.gadini.FinancialAnalysisML.domain.response.TransacaoResponse;
import com.github.gadini.FinancialAnalysisML.service.MetricasFinanceirasService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("metricas")
public class MetricasFinanceirasController {

    @Autowired
    MetricasFinanceirasService metricasFinanceirasService;

    @GetMapping
    public ResponseEntity<PagedModel<MetricasFinanceirasResponse>> list(Pageable page) {
        return ResponseEntity.ok(metricasFinanceirasService.listarMetricasFinanceiras(page));
    }

    @GetMapping("/empresa/{empresaId}")
    public ResponseEntity<PagedModel<MetricasFinanceirasResponse>> listByEmpresaId(@PathVariable Long empresaId, Pageable page) {
        return ResponseEntity.ok(metricasFinanceirasService.listarMetricasPorEmpresa(empresaId, page));
    }
}

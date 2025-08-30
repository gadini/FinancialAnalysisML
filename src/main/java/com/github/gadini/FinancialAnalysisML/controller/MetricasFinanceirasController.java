package com.github.gadini.FinancialAnalysisML.controller;

import com.github.gadini.FinancialAnalysisML.domain.request.PeriodoRequest;
import com.github.gadini.FinancialAnalysisML.domain.response.MetricasFinanceirasResponse;
import com.github.gadini.FinancialAnalysisML.service.MetricasFinanceirasService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("metricas")
public class MetricasFinanceirasController {

    @Autowired
    MetricasFinanceirasService metricasFinanceirasService;

    @PostMapping
    public ResponseEntity<Void> calculateMetricasFinanceiras (@RequestBody PeriodoRequest request){
        metricasFinanceirasService.calcularMetricasFinanceiras(request.getInicio(), request.getFim());
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<PagedModel<MetricasFinanceirasResponse>> list(Pageable page) {
        return ResponseEntity.ok(metricasFinanceirasService.listarMetricasFinanceiras(page));
    }

    @GetMapping("/empresa/{empresaId}")
    public ResponseEntity<PagedModel<MetricasFinanceirasResponse>> listByEmpresaId(@PathVariable Long empresaId, Pageable page) {
        return ResponseEntity.ok(metricasFinanceirasService.listarMetricasPorEmpresa(empresaId, page));
    }

    @GetMapping("/periodo")
    public ResponseEntity<PagedModel<MetricasFinanceirasResponse>> listByPeriodo(Pageable page, @RequestBody PeriodoRequest request) {
        return ResponseEntity.ok(metricasFinanceirasService.listarMetricasPorPeriodo(request, page));
    }
}

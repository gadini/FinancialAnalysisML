package com.github.gadini.FinancialAnalysisML.controller;

import com.github.gadini.FinancialAnalysisML.domain.request.PeriodoRequest;
import com.github.gadini.FinancialAnalysisML.service.RedeMetricasService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("network")
public class RedeMetricasController {

    @Autowired
    private RedeMetricasService redeMetricasService;

    @PostMapping
    public ResponseEntity<Void> createrRedeMetricas (@RequestBody PeriodoRequest request){
        redeMetricasService.calcularGrafoRedeMetricas(request.getInicio(), request.getFim());
        return ResponseEntity.ok().build();
    }
}

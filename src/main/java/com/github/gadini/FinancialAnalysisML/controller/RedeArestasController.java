package com.github.gadini.FinancialAnalysisML.controller;

import com.github.gadini.FinancialAnalysisML.domain.request.PeriodoRequest;
import com.github.gadini.FinancialAnalysisML.service.RedeArestasService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("arestas")
public class RedeArestasController {

    @Autowired
    private RedeArestasService redeArestasService;

    @PostMapping
    public ResponseEntity<Void> createRedeArestas (@RequestBody PeriodoRequest request){
        redeArestasService.criarRedeArestas(request.getInicio(), request.getFim());
        return ResponseEntity.ok().build();
    }
}

package com.github.gadini.FinancialAnalysisML.controller;

import com.github.gadini.FinancialAnalysisML.domain.request.PeriodoRequest;
import com.github.gadini.FinancialAnalysisML.service.ClassificacaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("classificacoes")
public class ClassificacaoController {

    @Autowired
    ClassificacaoService classificacaoService;

    @PostMapping
    public ResponseEntity<Void> insertClassificacao (@RequestBody PeriodoRequest request){
        classificacaoService.classificarPeriodo(request.getInicio(), request.getFim());
        return ResponseEntity.ok().build();
    }
}

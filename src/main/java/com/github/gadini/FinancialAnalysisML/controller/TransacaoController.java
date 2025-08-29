package com.github.gadini.FinancialAnalysisML.controller;

import com.github.gadini.FinancialAnalysisML.domain.response.EmpresaResponse;
import com.github.gadini.FinancialAnalysisML.domain.response.TransacaoResponse;
import com.github.gadini.FinancialAnalysisML.service.TransacaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("transacoes")
public class TransacaoController {

    @Autowired
    private TransacaoService transacaoService;

    @GetMapping
    public ResponseEntity<Page<TransacaoResponse>> listar(Pageable page){
        return ResponseEntity.ok(transacaoService.listarTransacoes(page));
    }
}

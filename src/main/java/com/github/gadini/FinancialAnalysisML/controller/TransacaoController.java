package com.github.gadini.FinancialAnalysisML.controller;

import com.github.gadini.FinancialAnalysisML.domain.response.TransacaoResponse;
import com.github.gadini.FinancialAnalysisML.service.TransacaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("transacoes")
public class TransacaoController {

    @Autowired
    private TransacaoService transacaoService;

    @GetMapping
    public ResponseEntity<PagedModel<TransacaoResponse>> list(Pageable page) {
        return ResponseEntity.ok(transacaoService.listarTransacoes(page));
    }

    @GetMapping("/empresa/{empresaId}")
    public ResponseEntity<PagedModel<TransacaoResponse>> listByEmpresaId(@PathVariable Long empresaId, Pageable page) {
        return ResponseEntity.ok(transacaoService.listarPorEmpresa(empresaId, page));
    }
}

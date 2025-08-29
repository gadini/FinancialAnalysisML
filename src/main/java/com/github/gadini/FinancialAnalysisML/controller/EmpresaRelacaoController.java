package com.github.gadini.FinancialAnalysisML.controller;

import com.github.gadini.FinancialAnalysisML.domain.response.ListAllEmpresaRelacaoResponse;
import com.github.gadini.FinancialAnalysisML.domain.response.ListEmpresaRelacaoByOrigemResponse;
import com.github.gadini.FinancialAnalysisML.service.EmpresaRelacaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("relacoes")
public class EmpresaRelacaoController {

    @Autowired
    private EmpresaRelacaoService empresaRelacaoService;

    @GetMapping
    public ResponseEntity<PagedModel<ListAllEmpresaRelacaoResponse>> list(Pageable page) {
        return ResponseEntity.ok(empresaRelacaoService.listarRelacoesEmpresas(page));
    }

    @GetMapping("/empresa/{empresaOrigemId}")
    public ResponseEntity<PagedModel<ListEmpresaRelacaoByOrigemResponse>> listByEmpresaOrigemId(@PathVariable Long empresaOrigemId, Pageable page) {
        return ResponseEntity.ok(empresaRelacaoService.listarRelacoesPorOrigem(empresaOrigemId, page));
    }

}

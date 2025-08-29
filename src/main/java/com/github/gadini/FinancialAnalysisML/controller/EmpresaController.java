package com.github.gadini.FinancialAnalysisML.controller;

import com.github.gadini.FinancialAnalysisML.domain.response.EmpresaResponse;
import com.github.gadini.FinancialAnalysisML.service.EmpresaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("empresas")
public class EmpresaController {

    @Autowired
    EmpresaService empresaService;

    @GetMapping
    public ResponseEntity<Page<EmpresaResponse>> list(Pageable page){
        return ResponseEntity.ok(empresaService.listarEmpresas(page));
    }
}

package com.github.gadini.FinancialAnalysisML.service;

import com.github.gadini.FinancialAnalysisML.domain.mapper.EmpresaMapper;
import com.github.gadini.FinancialAnalysisML.domain.response.EmpresaResponse;
import com.github.gadini.FinancialAnalysisML.persistence.repository.EmpresaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class EmpresaService {

    EmpresaMapper empresaMapper;

    @Autowired
    EmpresaRepository empresaRepository;

    public Page<EmpresaResponse> listarEmpresas(Pageable pageable){
        return empresaRepository.findAll(pageable).map(empresaMapper::toResponse);
    }
}

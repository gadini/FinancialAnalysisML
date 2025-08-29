package com.github.gadini.FinancialAnalysisML.service;

import com.github.gadini.FinancialAnalysisML.domain.mapper.EmpresaMapper;
import com.github.gadini.FinancialAnalysisML.domain.response.EmpresaResponse;
import com.github.gadini.FinancialAnalysisML.persistence.repository.EmpresaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedModel;
import org.springframework.stereotype.Service;

@Service
public class EmpresaService {

    @Autowired
    private EmpresaMapper empresaMapper;

    @Autowired
    private EmpresaRepository empresaRepository;

    public PagedModel<EmpresaResponse> listarEmpresas(Pageable pageable){
        return new PagedModel<>(empresaRepository.findAll(pageable).map(empresaMapper::toResponse));
    }
}

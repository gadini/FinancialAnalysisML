package com.github.gadini.FinancialAnalysisML.service;

import com.github.gadini.FinancialAnalysisML.domain.mapper.EmpresaRelacaoMapper;
import com.github.gadini.FinancialAnalysisML.domain.response.ListAllEmpresaRelacaoResponse;
import com.github.gadini.FinancialAnalysisML.domain.response.ListEmpresaRelacaoByOrigemResponse;
import com.github.gadini.FinancialAnalysisML.repository.EmpresaRelacaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedModel;
import org.springframework.stereotype.Service;

@Service
public class EmpresaRelacaoService {

    @Autowired
    private EmpresaRelacaoMapper empresaRelacaoMapper;

    @Autowired
    private EmpresaRelacaoRepository empresaRelacaoRepository;

    public PagedModel<ListAllEmpresaRelacaoResponse> listarRelacoesEmpresas(Pageable pageable){
        return new PagedModel<> (empresaRelacaoRepository.findAll(pageable).map(empresaRelacaoMapper::toListAllResponse));
    }

    public PagedModel<ListEmpresaRelacaoByOrigemResponse> listarRelacoesPorOrigem(Long empresaOrigemId, Pageable pageable) {
        return new PagedModel<>(empresaRelacaoRepository.findByEmpresaOrigemId(empresaOrigemId, pageable).map(empresaRelacaoMapper::toListByOrigemResponse));
    }

}

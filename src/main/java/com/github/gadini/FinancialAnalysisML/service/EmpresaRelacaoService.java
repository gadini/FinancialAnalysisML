package com.github.gadini.FinancialAnalysisML.service;

import com.github.gadini.FinancialAnalysisML.domain.mapper.EmpresaRelacaoMapper;
import com.github.gadini.FinancialAnalysisML.domain.response.ListAllEmpresaRelacaoResponse;
import com.github.gadini.FinancialAnalysisML.domain.response.TransacaoResponse;
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

//    public PagedModel<TransacaoResponse> listarPorEmpresa(Long empresaId, Pageable pageable) {
//        return new PagedModel<>(transacaoRepository.findByEmpresaId(empresaId, pageable).map(transacaoMapper::toResponse));
//    }
}

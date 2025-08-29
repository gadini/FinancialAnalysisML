package com.github.gadini.FinancialAnalysisML.service;

import com.github.gadini.FinancialAnalysisML.domain.mapper.TransacaoMapper;
import com.github.gadini.FinancialAnalysisML.domain.response.TransacaoResponse;
import com.github.gadini.FinancialAnalysisML.persistence.repository.TransacaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class TransacaoService {

    @Autowired
    TransacaoMapper transacaoMapper;

    @Autowired
    TransacaoRepository transacaoRepository;

    public Page<TransacaoResponse> listarTransacoes(Pageable pageable){
        return transacaoRepository.findAll(pageable).map(transacaoMapper::toResponse);
    }

    public Page<TransacaoResponse> listarPorEmpresa(Long empresaId, Pageable pageable) {
        return transacaoRepository.findByEmpresaId(empresaId, pageable)
                .map(transacaoMapper::toResponse);
    }
}

package com.github.gadini.FinancialAnalysisML.domain.mapper;

import com.github.gadini.FinancialAnalysisML.domain.entity.EmpresaRelacao;
import com.github.gadini.FinancialAnalysisML.domain.response.ListAllEmpresaRelacaoResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface EmpresaRelacaoMapper {

    @Mapping(source = "empresaOrigem.razaoSocial", target = "empresaOrigemNome")
    @Mapping(source = "empresaDestino.razaoSocial", target = "empresaDestinoNome")
    ListAllEmpresaRelacaoResponse  toListAllResponse(EmpresaRelacao empresaRelacao);
}

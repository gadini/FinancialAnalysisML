package com.github.gadini.FinancialAnalysisML.domain.mapper;

import com.github.gadini.FinancialAnalysisML.domain.entity.EmpresaRelacao;
import com.github.gadini.FinancialAnalysisML.domain.response.ListAllEmpresaRelacaoResponse;
import com.github.gadini.FinancialAnalysisML.domain.response.ListEmpresaRelacaoByOrigemResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface EmpresaRelacaoMapper {

    ListAllEmpresaRelacaoResponse  toListAllResponse(EmpresaRelacao empresaRelacao);

    ListEmpresaRelacaoByOrigemResponse toListByOrigemResponse(EmpresaRelacao empresaRelacao);
}

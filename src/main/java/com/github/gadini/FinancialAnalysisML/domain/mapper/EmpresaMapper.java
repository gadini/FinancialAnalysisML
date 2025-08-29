package com.github.gadini.FinancialAnalysisML.domain.mapper;

import com.github.gadini.FinancialAnalysisML.domain.response.EmpresaResponse;
import com.github.gadini.FinancialAnalysisML.persistence.entity.Empresa;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface EmpresaMapper {

    EmpresaResponse toResponse(Empresa empresa);
}

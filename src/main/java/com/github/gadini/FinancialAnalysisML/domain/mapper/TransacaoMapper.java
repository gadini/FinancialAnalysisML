package com.github.gadini.FinancialAnalysisML.domain.mapper;

import com.github.gadini.FinancialAnalysisML.domain.response.TransacaoResponse;
import com.github.gadini.FinancialAnalysisML.persistence.entity.Transacao;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface TransacaoMapper {

    @Mapping(target = "empresaId", source = "empresa.id")
    TransacaoResponse toResponse(Transacao transacao);
}

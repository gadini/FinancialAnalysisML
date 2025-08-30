package com.github.gadini.FinancialAnalysisML.domain.mapper;

import com.github.gadini.FinancialAnalysisML.domain.dto.MetricasFinanceirasDto;
import com.github.gadini.FinancialAnalysisML.domain.entity.MetricasFinanceiras;
import com.github.gadini.FinancialAnalysisML.domain.response.MetricasFinanceirasResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface MetricasFinanceirasMapper {

    @Mapping(source = "empresa.razaoSocial", target = "razaoSocial")
    MetricasFinanceirasResponse toResponse(MetricasFinanceiras metricasFinanceiras);

    @Mapping(source = "empresa.id", target = "empresaId")
    MetricasFinanceirasDto toDto(MetricasFinanceiras metricasFinanceiras);
}

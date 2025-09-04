package com.github.gadini.FinancialAnalysisML.domain.mapper;

import com.github.gadini.FinancialAnalysisML.domain.dto.MetricasFinanceirasDto;
import com.github.gadini.FinancialAnalysisML.domain.entity.MetricasFinanceiras;
import com.github.gadini.FinancialAnalysisML.domain.response.MetricasFinanceirasResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface MetricasFinanceirasMapper {

    MetricasFinanceirasResponse toResponse(MetricasFinanceiras metricasFinanceiras);

    MetricasFinanceirasDto toDto(MetricasFinanceiras metricasFinanceiras);
}

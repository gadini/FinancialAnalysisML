package com.github.gadini.FinancialAnalysisML.domain.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class MetricasFinanceirasDto {

    private Long empresaId;
    private LocalDate periodoInicio;
    private LocalDate periodoFim;
    private BigDecimal mediaRecebimentos;
    private BigDecimal mediaPagamentos;
    private Integer qtdClientes;
}

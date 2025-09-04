package com.github.gadini.FinancialAnalysisML.domain.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MetricasFinanceirasResponse {

    private Long empresaId;
    private LocalDate periodoInicio;
    private LocalDate periodoFim;
    private BigDecimal mediaRecebimentos;
    private BigDecimal mediaPagamentos;
    private Integer qtdClientes;
}

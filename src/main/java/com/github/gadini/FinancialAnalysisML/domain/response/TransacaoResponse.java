package com.github.gadini.FinancialAnalysisML.domain.response;

import com.github.gadini.FinancialAnalysisML.domain.enums.TipoTransacaoEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TransacaoResponse {

    private Long empresaId;
    private LocalDate data;
    private TipoTransacaoEnum tipo;
    private BigDecimal valor;
    private String descricao;

}

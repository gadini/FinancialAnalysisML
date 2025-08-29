package com.github.gadini.FinancialAnalysisML.domain.response;

import com.github.gadini.FinancialAnalysisML.domain.enums.StatusEnum;
import com.github.gadini.FinancialAnalysisML.domain.enums.TipoRelacaoEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ListAllEmpresaRelacaoResponse {

    private Long id;
    private String empresaOrigemNome;
    private TipoRelacaoEnum tipoRelacao;
    private String empresaDestinoNome;
    private LocalDate dataInicio;
    private LocalDate dataFim;
    private StatusEnum status;
}

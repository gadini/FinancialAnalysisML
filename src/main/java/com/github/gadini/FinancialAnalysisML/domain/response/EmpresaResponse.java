package com.github.gadini.FinancialAnalysisML.domain.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmpresaResponse {

    private Long id;
    private String idExterno;
    private Long valorFaturamento;
    private Long valorSaldo;
    private LocalDate dataAbertura;
    private String cnae;
    private LocalDate dataReferencia;

}

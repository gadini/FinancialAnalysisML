package com.github.gadini.FinancialAnalysisML.domain.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmpresaResponse {

    private String cnpj;
    private String razaoSocial;
    private String nomeFantasia;
    private LocalDate dataAbertura;
    private String segmento;

}

package com.github.gadini.FinancialAnalysisML.domain.enums;

import lombok.Getter;

@Getter
public enum TipoTransacaoEnum {

    PIX("PIX"),
    TED("TED"),
    BOLETO("Boleto"),
    SISTEMICO("Sistemico");

    private final String descricao;

    TipoTransacaoEnum(String descricao) {
        this.descricao = descricao;
    }
}

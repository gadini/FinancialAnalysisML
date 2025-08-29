package com.github.gadini.FinancialAnalysisML.domain.enums;

import lombok.Getter;

@Getter
public enum TipoRelacaoEnum {

    FORNECEDOR("Fornecedor"),
    CLIENTE("Cliente"),
    PARCEIRA("Parceira"),
    INVESTIDORA("Investidora");

    private final String descricao;

    TipoRelacaoEnum(String descricao) {
        this.descricao = descricao;
    }
}

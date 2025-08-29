package com.github.gadini.FinancialAnalysisML.domain.enums;

import lombok.Getter;

@Getter
public enum StatusEnum {

    ATIVO("Ativo"),
    INATIVO("Inativo");

    private final String descricao;

    StatusEnum(String descricao) {
        this.descricao = descricao;
    }
}

package com.github.gadini.FinancialAnalysisML.domain.enums;

import lombok.Getter;

@Getter
public enum MomentoEnum {

    INICIO("Inicio"),
    EXPANSAO("Expansao"),
    MATURIDADE("Maturidade"),
    DECLINIO("Declinio");

    private final String descricao;

    MomentoEnum(String descricao) {
        this.descricao = descricao;
    }
}

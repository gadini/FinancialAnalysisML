package com.github.gadini.FinancialAnalysisML.domain.enums;

import lombok.Getter;

@Getter
public enum TipoTransacaoEnum {

    RECEBIMENTO("Recebimento de valores"),
    PAGAMENTO("Pagamento de despesas"),
    CREDITO("Operação de crédito"),
    INVESTIMENTO("Aplicação em investimento"),
    INADIMPLENCIA("Registro de inadimplência");

    private final String descricao;

    TipoTransacaoEnum(String descricao) {
        this.descricao = descricao;
    }
}

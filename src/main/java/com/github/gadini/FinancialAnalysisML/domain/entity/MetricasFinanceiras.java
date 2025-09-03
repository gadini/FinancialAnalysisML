package com.github.gadini.FinancialAnalysisML.domain.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "metricas_financeiras")
public class MetricasFinanceiras extends BaseEntity {

    @Column(name = "empresa_id", nullable = false)
    private Long empresaId;

    @Column(name = "periodo_inicio")
    private LocalDate periodoInicio;

    @Column(name = "periodo_fim")
    private LocalDate periodoFim;

    @Column(name = "media_recebimentos", precision = 15, scale = 2)
    private BigDecimal mediaRecebimentos;

    @Column(name = "media_pagamentos", precision = 15, scale = 2)
    private BigDecimal mediaPagamentos;

    @Column(name = "qtd_clientes")
    private Integer qtdClientes;

}

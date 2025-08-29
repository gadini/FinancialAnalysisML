package com.github.gadini.FinancialAnalysisML.domain.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "empresa_id", nullable = false)
    private Empresa empresa;

    @Column(name = "periodo_inicio")
    private LocalDate periodoInicio;

    @Column(name = "periodo_fim")
    private LocalDate periodoFim;

    @Column(name = "media_recebimentos", precision = 15, scale = 2)
    private BigDecimal mediaRecebimentos;

    @Column(name = "media_pagamentos", precision = 15, scale = 2)
    private BigDecimal mediaPagamentos;

    @Column(name = "volume_credito", precision = 15, scale = 2)
    private BigDecimal volumeCredito;

    @Column(name = "total_investimentos", precision = 15, scale = 2)
    private BigDecimal totalInvestimentos;

    @Column(name = "inadimplencia_percentual", precision = 5, scale = 2)
    private BigDecimal inadimplenciaPercentual;

    @Column(name = "qtd_clientes")
    private Integer qtdClientes;

}

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
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "rede_metricas")
public class RedeMetricas extends BaseEntity {

    @Column(name = "periodo_inicio", nullable = false)
    private LocalDate periodoInicio;

    @Column(name = "periodo_fim", nullable = false)
    private LocalDate periodoFim;

    @Column(name = "id_externo", nullable = false, length = 64)
    private String idExterno;

    @Column(name = "degree_out", precision = 10, scale = 6)
    private BigDecimal degreeOut;

    @Column(name = "degree_in", precision = 10, scale = 6)
    private BigDecimal degreeIn;

    @Column(name = "pagerank", precision = 10, scale = 6)
    private BigDecimal pageRank;

    @Column(name = "betweenness", precision = 18, scale = 8)
    private BigDecimal betweenness;

    @Column(name = "eigenvector", precision = 10, scale = 6)
    private BigDecimal eigenvector;

    @Column(name = "exposure_out", precision = 10, scale = 6)
    private BigDecimal exposureOut;

    @Column(name = "comunidade")
    private Long comunidade;
}

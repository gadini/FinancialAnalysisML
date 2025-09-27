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
@Table(name = "rede_arestas")
public class RedeArestas extends BaseEntity {

    @Column(name = "periodo_inicio", nullable = false)
    private LocalDate periodoInicio;

    @Column(name = "periodo_fim", nullable = false)
    private LocalDate periodoFim;

    @Column(name = "u", nullable = false, length = 64)
    private String u;

    @Column(name = "v", nullable = false, length = 64)
    private String v;

    @Column(name = "vol_uv", nullable = false)
    private Long volUv;

    @Column(name = "dep_out", nullable = false, precision = 10, scale = 6)
    private BigDecimal depOut;

    @Column(name = "dep_in", nullable = false, precision = 10, scale = 6)
    private BigDecimal depIn;

}

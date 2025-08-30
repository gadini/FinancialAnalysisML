package com.github.gadini.FinancialAnalysisML.domain.entity;

import com.github.gadini.FinancialAnalysisML.domain.enums.MomentoEnum;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "classificacao")
public class Classificacao extends BaseEntity{

    @Column(name = "empresa_id")
    private Long empresaId;

    @Enumerated(EnumType.STRING)
    @Column(name = "momento", nullable = false)
    private MomentoEnum momento;

    @Column(name = "score_analitico", precision = 5, scale = 2)
    private BigDecimal scoreAnalitico;

    @Column(name = "data_analise", nullable = false)
    private LocalDate dataAnalise;
}

package com.github.gadini.FinancialAnalysisML.domain.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "empresa")
public class Empresa extends BaseEntity {

    @Column(name = "id_externo", nullable = false, length = 64)
    private String idExterno;

    @Column(name = "vl_fatu")
    private Long valorFaturamento;

    @Column(name = "vl_sldo")
    private Long valorSaldo;

    @Column(name = "dt_abrt")
    private LocalDate dataAbertura;

    @Column(name = "ds_cnae", length = 255)
    private String cnae;

    @Column(name = "dt_refe")
    private LocalDate dataReferencia;
}

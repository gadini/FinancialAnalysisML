package com.github.gadini.FinancialAnalysisML.domain.entity;

import com.github.gadini.FinancialAnalysisML.domain.enums.TipoTransacaoEnum;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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
@Table(name = "transacao")
public class Transacao extends BaseEntity {

    @Column(name = "id_pgto", nullable = false, length = 64)
    private String idPagamento;

    @Column(name = "id_rcbe", nullable = false, length = 64)
    private String idRcbe;

    @Column(name = "vl", nullable = false)
    private Long valor;

    @Enumerated(EnumType.STRING)
    @Column(name = "ds_tran", nullable = false)
    private TipoTransacaoEnum tipo;

    @Column(name = "dt_refe", nullable = false)
    private LocalDate dataReferencia;
}

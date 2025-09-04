package com.github.gadini.FinancialAnalysisML.domain.entity;

import com.github.gadini.FinancialAnalysisML.domain.enums.StatusEnum;
import com.github.gadini.FinancialAnalysisML.domain.enums.TipoRelacaoEnum;
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

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "empresa_relacao")
public class EmpresaRelacao extends BaseEntity {

    @Column(name = "empresa_origem_id", nullable = false)
    private String empresaOrigem;

    @Column(name = "empresa_destino_id", nullable = false)
    private String empresaDestino;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_relacao", nullable = false, length = 20)
    private TipoRelacaoEnum tipoRelacao;

    @Column(name = "data_inicio", nullable = false)
    private LocalDate dataInicio;

    @Column(name = "data_fim", nullable = false)
    private LocalDate dataFim;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 20)
    private StatusEnum status;
}

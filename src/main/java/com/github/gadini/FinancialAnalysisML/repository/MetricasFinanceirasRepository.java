package com.github.gadini.FinancialAnalysisML.repository;

import com.github.gadini.FinancialAnalysisML.domain.entity.MetricasFinanceiras;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;

import java.time.LocalDate;

public interface MetricasFinanceirasRepository extends JpaRepository<MetricasFinanceiras, Long> {

    Page<MetricasFinanceiras> findByEmpresaId(Long empresaId, Pageable pageable);

    @Procedure(procedureName = "sp_calcular_metricas_por_periodo")
    void spCalcularMetricasPorPeriodo(LocalDate p_inicio, LocalDate p_fim);
}

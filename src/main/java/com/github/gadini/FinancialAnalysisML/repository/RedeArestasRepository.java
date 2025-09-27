package com.github.gadini.FinancialAnalysisML.repository;

import com.github.gadini.FinancialAnalysisML.domain.entity.RedeArestas;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;

import java.time.LocalDate;

public interface RedeArestasRepository extends JpaRepository<RedeArestas, Long> {

    @Procedure(procedureName = "sp_criar_rede_arestas")
    void spCriarRedeArestas(LocalDate p_inicio, LocalDate p_fim);

}

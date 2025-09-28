package com.github.gadini.FinancialAnalysisML.repository;

import com.github.gadini.FinancialAnalysisML.domain.entity.Classificacao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface ClassificacaoRepository extends JpaRepository<Classificacao, Long> {

    interface IdExternoMomento {
        String getIdExterno();
        String getMomento();
    }

    @Query(value = """
        SELECT e.id_externo AS idExterno, c.momento AS momento
          FROM classificacao c
          JOIN empresa e ON e.id = c.empresa_id
         WHERE c.data_analise = :data
        """, nativeQuery = true)
    List<IdExternoMomento> findIdExternoAndMomentoByData(@Param("data") LocalDate data);

}

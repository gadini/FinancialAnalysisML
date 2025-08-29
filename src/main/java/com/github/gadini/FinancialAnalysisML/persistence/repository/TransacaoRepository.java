package com.github.gadini.FinancialAnalysisML.persistence.repository;

import com.github.gadini.FinancialAnalysisML.persistence.entity.Transacao;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransacaoRepository extends JpaRepository<Transacao, Long> {
}

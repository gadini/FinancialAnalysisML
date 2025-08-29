package com.github.gadini.FinancialAnalysisML.repository;

import com.github.gadini.FinancialAnalysisML.domain.entity.EmpresaRelacao;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmpresaRelacaoRepository extends JpaRepository<EmpresaRelacao, Long> {
    Page<EmpresaRelacao> findByEmpresaOrigemId(Long empresaOrigemId, Pageable pageable);
}

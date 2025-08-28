package com.github.gadini.FinancialAnalysisML.persistence.repository;

import com.github.gadini.FinancialAnalysisML.persistence.entity.Empresa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmpresaRepository extends JpaRepository<Empresa, Long> {
}

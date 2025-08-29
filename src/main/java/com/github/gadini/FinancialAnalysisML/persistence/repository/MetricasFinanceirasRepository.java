package com.github.gadini.FinancialAnalysisML.persistence.repository;

import com.github.gadini.FinancialAnalysisML.persistence.entity.MetricasFinanceiras;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MetricasFinanceirasRepository extends JpaRepository<MetricasFinanceiras, Long> {
}

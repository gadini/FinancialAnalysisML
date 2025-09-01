package com.github.gadini.FinancialAnalysisML.domain.dto;

import com.github.gadini.FinancialAnalysisML.domain.enums.MomentoEnum;
import lombok.Data;

import java.time.LocalDate;

@Data
public class ClassificacaoDto {

    private Long empresaId;
    private MomentoEnum momento;
    private LocalDate dataAnalise;
}

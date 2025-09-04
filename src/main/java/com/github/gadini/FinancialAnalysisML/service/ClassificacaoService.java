package com.github.gadini.FinancialAnalysisML.service;

import com.github.gadini.FinancialAnalysisML.domain.dto.ClassificacaoDto;
import com.github.gadini.FinancialAnalysisML.domain.dto.MetricasFinanceirasDto;
import com.github.gadini.FinancialAnalysisML.domain.enums.MomentoEnum;
import com.github.gadini.FinancialAnalysisML.domain.mapper.ClassificacaoMapper;
import com.github.gadini.FinancialAnalysisML.domain.mapper.MetricasFinanceirasMapper;
import com.github.gadini.FinancialAnalysisML.repository.ClassificacaoRepository;
import com.github.gadini.FinancialAnalysisML.repository.MetricasFinanceirasRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import smile.clustering.KMeans;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.List;

@Slf4j
@Service
public class ClassificacaoService {

    @Autowired
    private MetricasFinanceirasMapper metricasFinanceirasMapper;

    @Autowired
    private MetricasFinanceirasRepository metricasFinanceirasRepository;

    @Autowired
    private ClassificacaoRepository classificacaoRepository;

    @Autowired
    private ClassificacaoMapper classificacaoMapper;

    @Transactional
    public void classificarPeriodo(LocalDate inicio, LocalDate fim) {
        List<MetricasFinanceirasDto> metricas =
                metricasFinanceirasRepository.findByPeriodoInicioAndPeriodoFim(inicio, fim).stream().map(metricasFinanceirasMapper::toDto).toList();

        if (metricas.isEmpty()) {
            log.warn("Nenhuma métrica financeira encontrada para o período {} - {}", inicio, fim);
            return;
        }

        double[][] data = metricas.stream().map(this::toArray).toArray(double[][]::new);

        KMeans kmeans = KMeans.fit(data, MomentoEnum.values().length);
        //double[] silhouette = Silhouette.of(data, kmeans.y);

        for (int i = 0; i < metricas.size(); i++) {
            ClassificacaoDto classificacao = new ClassificacaoDto();
            classificacao.setEmpresaId(metricas.get(i).getEmpresaId());
            classificacao.setMomento(MomentoEnum.values()[kmeans.y[i]]);
            //classificacao.setScoreAnalitico(BigDecimal.valueOf(silhouette[i]));
            classificacao.setDataAnalise(fim.with(TemporalAdjusters.lastDayOfMonth()));
            classificacaoRepository.save(classificacaoMapper.toEntity(classificacao));
        }
    }

    private double[] toArray(MetricasFinanceirasDto m) {
        return new double[]{
                toDouble(m.getMediaRecebimentos()),
                toDouble(m.getMediaPagamentos()),
                //m.getQtdClientes() != null ? m.getQtdClientes() : 0
        };
    }

    private double toDouble(BigDecimal value) {
        return value != null ? value.doubleValue() : 0.0;
    }

}

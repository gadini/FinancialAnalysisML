package com.github.gadini.FinancialAnalysisML.service;

import com.github.gadini.FinancialAnalysisML.domain.dto.ClassificacaoDto;
import com.github.gadini.FinancialAnalysisML.domain.dto.MetricasFinanceirasDto;
import com.github.gadini.FinancialAnalysisML.domain.entity.Classificacao;
import com.github.gadini.FinancialAnalysisML.domain.enums.MomentoEnum;
import com.github.gadini.FinancialAnalysisML.domain.mapper.ClassificacaoMapper;
import com.github.gadini.FinancialAnalysisML.domain.mapper.MetricasFinanceirasMapper;
import com.github.gadini.FinancialAnalysisML.repository.ClassificacaoRepository;
import com.github.gadini.FinancialAnalysisML.repository.MetricasFinanceirasRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import smile.clustering.KMeans;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Slf4j
@Service
@RequiredArgsConstructor
public class ClassificacaoService {

    private final MetricasFinanceirasRepository metricasFinanceirasRepository;
    private final ClassificacaoRepository classificacaoRepository;
    private final MetricasFinanceirasMapper metricasFinanceirasMapper;
    private final ClassificacaoMapper classificacaoMapper;

    @Transactional
    public void classificarPeriodo(LocalDate inicio, LocalDate fim) {
        var metricasEntities = metricasFinanceirasRepository
                .findByPeriodoInicioAndPeriodoFim(inicio, fim);

        if (metricasEntities == null || metricasEntities.isEmpty()) {
            log.warn("Nenhuma métrica financeira encontrada para o período {} - {}", inicio, fim);
            return;
        }

        var metricas = metricasEntities.stream()
                .map(metricasFinanceirasMapper::toDto)
                .toList();

        final int n = metricas.size();
        final int d = 3;
        double[][] X = new double[n][d];
        long[] empresaIds = new long[n];

        for (int i = 0; i < n; i++) {
            MetricasFinanceirasDto m = metricas.get(i);
            empresaIds[i] = m.getEmpresaId();

            double rec = toDouble(m.getMediaRecebimentos());
            double pag = toDouble(m.getMediaPagamentos());
            double cli = m.getQtdClientes() != null ? m.getQtdClientes() : 0;

            X[i][0] = rec;
            X[i][1] = pag;
            X[i][2] = cli;
        }

        final int K_MAX = MomentoEnum.values().length; // 4
        int k = Math.min(K_MAX, distinctCount(empresaIds));
        if (k < 2) {
            log.warn("Apenas {} empresa(s) no período {} - {}. Classificação ignorada.", k, inicio, fim);
            return;
        }

        KMeans kmeans = KMeans.fit(X, k);
        log.info("KMeans: k={}, distortion={}", k, kmeans.distortion);

        Map<Integer, MomentoEnum> clusterToMomento = mapClustersSimple(kmeans.centroids);

        LocalDate dataAnalise = fim.with(TemporalAdjusters.lastDayOfMonth());
        List<Classificacao> batch = new ArrayList<>(n);
        for (int i = 0; i < n; i++) {
            var dto = new ClassificacaoDto();
            dto.setEmpresaId(empresaIds[i]);
            dto.setMomento(clusterToMomento.getOrDefault(kmeans.y[i], MomentoEnum.INICIO));
            dto.setDataAnalise(dataAnalise);
            batch.add(classificacaoMapper.toEntity(dto));
        }
        classificacaoRepository.saveAll(batch);
        log.info("Classificação concluída para {} empresas no período {} - {}", n, inicio, fim);
    }

    private static double toDouble(BigDecimal v) {
        return v != null ? v.doubleValue() : 0.0;
    }

    private static int distinctCount(long[] arr) {
        var set = new HashSet<Long>(arr.length * 2);
        for (long v : arr) set.add(v);
        return set.size();
    }

    /**
     * Regras estáveis:
     *  - DECLINIO  : menor NET  (net = rec - pag)
     *  - MATURIDADE: maior NET, desempate por maior TOTAL (rec + pag)
     *  - EXPANSAO  : entre os restantes, maior PAG (preferindo net < 0, se existir)
     *  - INICIO    : entre os restantes, **menor TOTAL**
     */
    private static Map<Integer, MomentoEnum> mapClustersSimple(double[][] C) {
        record CInfo(int idx, double rec, double pag, double cli, double total, double net) {}
        List<CInfo> infos = new ArrayList<>(C.length);
        for (int c = 0; c < C.length; c++) {
            double rec = C[c][0], pag = C[c][1], cli = C[c][2];
            infos.add(new CInfo(c, rec, pag, cli, rec + pag, rec - pag));
        }

        var decl = infos.stream()
                .min(Comparator.comparingDouble(ci -> ci.net))
                .orElseThrow();

        var mat = infos.stream()
                .max(Comparator.<CInfo>comparingDouble(ci -> ci.net)
                        .thenComparingDouble(ci -> ci.total))
                .orElseThrow();

        Set<Integer> usados = new HashSet<>(List.of(decl.idx, mat.idx));
        var candidatos = infos.stream().filter(ci -> !usados.contains(ci.idx)).toList();

        CInfo exp = candidatos.stream()
                .filter(ci -> ci.net < 0)
                .max(Comparator.comparingDouble(ci -> ci.pag))
                .orElseGet(() ->
                        candidatos.stream()
                                .max(Comparator.comparingDouble(ci -> ci.pag))
                                .orElse(null)
                );

        CInfo ini = infos.stream()
                .filter(ci -> ci.idx != decl.idx && ci.idx != mat.idx && (exp == null || ci.idx != exp.idx))
                .min(Comparator.comparingDouble(ci -> ci.total))
                .orElse(null);

        Map<Integer, MomentoEnum> map = new HashMap<>();
        map.put(decl.idx, MomentoEnum.DECLINIO);
        map.put(mat.idx,  MomentoEnum.MATURIDADE);
        if (exp != null) map.put(exp.idx,  MomentoEnum.EXPANSAO);
        if (ini != null) map.put(ini.idx,  MomentoEnum.INICIO);

        for (CInfo ci : infos) map.putIfAbsent(ci.idx, MomentoEnum.INICIO);
        return map;
    }
}

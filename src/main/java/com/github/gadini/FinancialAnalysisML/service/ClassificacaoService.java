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
        final int d = 3; // [media_receb, media_pag, qtd_clientes]
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

        zscoreInPlace(X);

        final int K_MAX = MomentoEnum.values().length;
        int k = Math.min(K_MAX, distinctCount(empresaIds));
        if (k < 2) {
            log.warn("Apenas {} empresa(s) no período {} - {}. Classificação ignorada.", k, inicio, fim);
            return;
        }

        KMeans kmeans = KMeans.fit(X, k);
        log.info("KMeans: k={}, distortion={}", k, kmeans.distortion);

        Map<Integer, MomentoEnum> clusterToMomento = mapClusters(kmeans.centroids);

        LocalDate dataAnalise = fim.with(TemporalAdjusters.lastDayOfMonth());
        List<Classificacao> batch = new ArrayList<>(n);

        for (int i = 0; i < n; i++) {
            MomentoEnum momento = clusterToMomento.getOrDefault(kmeans.y[i], MomentoEnum.INICIO);

            ClassificacaoDto dto = new ClassificacaoDto();
            dto.setEmpresaId(empresaIds[i]);
            dto.setMomento(momento);
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

    private static void zscoreInPlace(double[][] X) {
        int n = X.length;
        if (n == 0) return;
        int d = X[0].length;

        double[] mean = new double[d];
        double[] std = new double[d];

        for (int j = 0; j < d; j++) {
            double s = 0.0;
            for (int i = 0; i < n; i++) s += X[i][j];
            mean[j] = s / n;

            double v = 0.0;
            for (int i = 0; i < n; i++) {
                double diff = X[i][j] - mean[j];
                v += diff * diff;
            }
            std[j] = Math.sqrt(v / Math.max(1, n - 1));
            if (std[j] == 0.0) std[j] = 1.0;

            for (int i = 0; i < n; i++) {
                X[i][j] = (X[i][j] - mean[j]) / std[j];
            }
        }
    }

    private static Map<Integer, MomentoEnum> mapClusters(double[][] C) {
        record CInfo(int idx, double rec, double pag, double cli, double total, double net) {}

        List<CInfo> infos = new ArrayList<>(C.length);
        for (int c = 0; c < C.length; c++) {
            double rec = C[c][0];
            double pag = C[c][1];
            double cli = C[c][2];
            double total = rec + pag;
            double net = rec - pag;
            infos.add(new CInfo(c, rec, pag, cli, total, net));
        }

        int inicioIdx = infos.stream()
                .min(Comparator.comparingDouble((CInfo ci) -> ci.total)
                        .thenComparingDouble(ci -> ci.cli))
                .map(ci -> ci.idx).orElse(0);

        int maturIdx = infos.stream()
                .max(Comparator.comparingDouble((CInfo ci) -> (ci.total + ci.cli) - Math.abs(ci.net)))
                .map(ci -> ci.idx).orElse(0);

        Set<Integer> used = new HashSet<>(List.of(inicioIdx, maturIdx));
        List<CInfo> rest = infos.stream().filter(ci -> !used.contains(ci.idx)).toList();
        int expansaoIdx = rest.isEmpty()
                ? maturIdx
                : rest.stream().max(Comparator.comparingDouble(ci -> ci.net)).map(ci -> ci.idx).orElse(maturIdx);
        int declinioIdx = rest.isEmpty()
                ? inicioIdx
                : rest.stream().min(Comparator.comparingDouble(ci -> ci.net)).map(ci -> ci.idx).orElse(inicioIdx);

        Map<Integer, MomentoEnum> map = new HashMap<>();
        map.put(inicioIdx,    MomentoEnum.INICIO);
        map.put(expansaoIdx,  MomentoEnum.EXPANSAO);
        map.put(maturIdx,     MomentoEnum.MATURIDADE);
        map.put(declinioIdx,  MomentoEnum.DECLINIO);
        return map;
    }
}


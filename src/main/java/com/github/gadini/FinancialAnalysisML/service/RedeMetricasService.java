package com.github.gadini.FinancialAnalysisML.service;

import com.github.gadini.FinancialAnalysisML.domain.entity.RedeArestas;
import com.github.gadini.FinancialAnalysisML.domain.entity.RedeMetricas;
import com.github.gadini.FinancialAnalysisML.domain.enums.MomentoEnum;
import com.github.gadini.FinancialAnalysisML.repository.ClassificacaoRepository;
import com.github.gadini.FinancialAnalysisML.repository.RedeArestasRepository;
import com.github.gadini.FinancialAnalysisML.repository.RedeMetricasRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jgrapht.Graphs;
import org.jgrapht.alg.clustering.LabelPropagationClustering;
import org.jgrapht.alg.interfaces.ClusteringAlgorithm;
import org.jgrapht.alg.scoring.BetweennessCentrality;
import org.jgrapht.alg.scoring.EigenvectorCentrality;
import org.jgrapht.alg.scoring.PageRank;
import org.jgrapht.graph.DefaultDirectedWeightedGraph;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class RedeMetricasService {

    private final RedeArestasRepository redeArestasRepository;
    private final RedeMetricasRepository redeMetricasRepository;
    private final ClassificacaoRepository classificacaoRepository;

    @Transactional
    public void calcularGrafoRedeMetricas(LocalDate inicio, LocalDate fim) {
        List<RedeArestas> arestas = redeArestasRepository.findByPeriodoInicioAndPeriodoFim(inicio, fim);
        if (arestas == null || arestas.isEmpty()) {
            log.warn("Sem arestas em rede_arestas para período {} - {}", inicio, fim);
            return;
        }

        var G = new DefaultDirectedWeightedGraph<String, DefaultWeightedEdge>(DefaultWeightedEdge.class);
        for (RedeArestas ra : arestas) {
            String u = ra.getU();
            String v = ra.getV();
            G.addVertex(u);
            G.addVertex(v);
            var e = G.addEdge(u, v);
            if (e != null) G.setEdgeWeight(e, toDouble(ra.getDepOut()));
        }

        var pageRank    = new PageRank<>(G);
        var betweenness = new BetweennessCentrality<>(G, true);
        var eigenvector = new EigenvectorCentrality<>(G);

        Map<String, Double> degreeOut = new HashMap<>();
        Map<String, Double> degreeIn  = new HashMap<>();
        for (String v : G.vertexSet()) {
            degreeOut.put(v, G.outgoingEdgesOf(v).stream().mapToDouble(G::getEdgeWeight).sum());
            degreeIn .put(v, G.incomingEdgesOf(v).stream().mapToDouble(G::getEdgeWeight).sum());
        }

        Map<String, Double> baseRisk = carregarRiscoPorClassificacao(fim);

        Map<String, Double> exposureOut = new HashMap<>();
        for (String u : G.vertexSet()) {
            double s = 0.0;
            for (DefaultWeightedEdge e : G.outgoingEdgesOf(u)) {
                String v = Graphs.getOppositeVertex(G, e, u);
                s += baseRisk.getOrDefault(v, 0.0) * G.getEdgeWeight(e);
            }
            exposureOut.put(u, s);
        }

        var GU = new SimpleWeightedGraph<String, DefaultWeightedEdge>(DefaultWeightedEdge.class);
        for (String v : G.vertexSet()) GU.addVertex(v);
        for (DefaultWeightedEdge e : G.edgeSet()) {
            String u = G.getEdgeSource(e);
            String v = G.getEdgeTarget(e);
            double w = G.getEdgeWeight(e);

            DefaultWeightedEdge und = GU.getEdge(u, v);
            if (und == null) {
                und = GU.addEdge(u, v);
                GU.setEdgeWeight(und, w);
            } else {
                GU.setEdgeWeight(und, GU.getEdgeWeight(und) + w); // soma pesos recíprocos
            }
        }
        LabelPropagationClustering<String, DefaultWeightedEdge> lpa = new LabelPropagationClustering<>(GU);
        ClusteringAlgorithm.Clustering<String> clustering = lpa.getClustering();
        Map<String, Long> comunidade = new HashMap<>();
        long cid = 0;
        for (var cluster : clustering.getClusters()) {
            for (String v : cluster) comunidade.put(v, cid);
            cid++;
        }

        List<RedeMetricas> batch = new ArrayList<>(G.vertexSet().size());
        for (String v : G.vertexSet()) {
            var rm = new RedeMetricas();
            rm.setPeriodoInicio(inicio);
            rm.setPeriodoFim(fim);
            rm.setIdExterno(v);
            rm.setDegreeOut(toBigDecimal(degreeOut.getOrDefault(v, 0.0), 6));
            rm.setDegreeIn (toBigDecimal(degreeIn .getOrDefault(v, 0.0), 6));
            rm.setPageRank  (toBigDecimal(pageRank.getVertexScore(v),      6));
            rm.setBetweenness(toBigDecimal(betweenness.getVertexScore(v),   8));
            rm.setEigenvector(toBigDecimal(eigenvector.getVertexScore(v),   6));
            rm.setExposureOut(toBigDecimal(exposureOut.getOrDefault(v, 0.0),6));
            rm.setComunidade(comunidade.get(v));
            batch.add(rm);
        }
        redeMetricasRepository.saveAll(batch);

        log.info("rede_metricas persistida: {} - {} | V={} E={}", inicio, fim, G.vertexSet().size(), G.edgeSet().size());
    }

    private Map<String, Double> carregarRiscoPorClassificacao(LocalDate fim) {
        Map<String, Double> map = new HashMap<>();
        LocalDate dataAnalise = fim.with(TemporalAdjusters.lastDayOfMonth());
        var rows = classificacaoRepository.findIdExternoAndMomentoByData(dataAnalise);
        for (var r : rows) {
            map.put(r.getIdExterno(), riscoPorMomento(r.getMomento()));
        }
        return map;
    }

    private double riscoPorMomento(String momento) {
        if (momento == null) return 0.0;
        try {
            return switch (MomentoEnum.valueOf(momento)) {
                case DECLINIO   -> 0.90;
                case INICIO     -> 0.60;
                case EXPANSAO   -> 0.30;
                case MATURIDADE -> 0.20;
            };
        } catch (IllegalArgumentException e) {
            return 0.40;
        }
    }

    private static double toDouble(BigDecimal b) {
        return b != null ? b.doubleValue() : 0.0;
    }
    private static BigDecimal toBigDecimal(double v, int scale) {
        return BigDecimal.valueOf(v).setScale(scale, RoundingMode.HALF_UP);
    }
}

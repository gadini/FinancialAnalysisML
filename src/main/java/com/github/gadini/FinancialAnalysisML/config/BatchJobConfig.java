package com.github.gadini.FinancialAnalysisML.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = "financial.analysis.job")
public class BatchJobConfig {

    private InsertMetricasFinanceirasJobProperties insertMetricasFinanceiras;
    private InsertClassificacaoJobProperties insertClassificacao;
    private InsertRedeArestasJobProperties insertRedeArestas;
    private InsertRedeMetricasJobProperties insertRedeMetricas;

    @Getter
    @Setter
    public static class InsertMetricasFinanceirasJobProperties {
        private String cron;
        private boolean enable;
        private int pageSize;
    }

    @Getter
    @Setter
    public static class InsertClassificacaoJobProperties {
        private String cron;
        private boolean enable;
        private int pageSize;
    }

    @Getter
    @Setter
    public static class InsertRedeArestasJobProperties {
        private String cron;
        private boolean enable;
        private int pageSize;
    }

    @Getter
    @Setter
    public static class InsertRedeMetricasJobProperties {
        private String cron;
        private boolean enable;
        private int pageSize;
    }
}

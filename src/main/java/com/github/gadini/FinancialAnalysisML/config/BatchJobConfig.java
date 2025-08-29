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

    @Getter
    @Setter
    public static class InsertMetricasFinanceirasJobProperties {
        private String cron;
        private boolean enable;
        private int pageSize;
    }
}

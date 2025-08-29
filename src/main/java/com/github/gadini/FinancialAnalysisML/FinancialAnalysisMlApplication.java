package com.github.gadini.FinancialAnalysisML;

import com.github.gadini.FinancialAnalysisML.config.BatchJobConfig;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableBatchProcessing
@EnableScheduling
@EnableConfigurationProperties({ BatchJobConfig.class })
public class FinancialAnalysisMlApplication {

	public static void main(String[] args) {
		SpringApplication.run(FinancialAnalysisMlApplication.class, args);
	}

}

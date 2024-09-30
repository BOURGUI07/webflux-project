package com.example.aggregator_service.config;

import com.example.aggregator_service.client.CustomerServiceClient;
import com.example.aggregator_service.client.StockServiceClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
@Slf4j
public class ServiceClientConfig {
    @Bean
    public CustomerServiceClient getCustomerServiceClient(
            @Value("${customer.service.url}") String baseUrl
    ) {
        return new CustomerServiceClient(createWebClient(baseUrl));
    }

    @Bean
    public StockServiceClient getStockServiceClient(
            @Value("${stock.service.url}") String baseUrl
    ) {
        return new StockServiceClient(createWebClient(baseUrl));
    }

    public WebClient createWebClient(String baseUrl) {
        log.info("RECEIVED BASE URL: {}", baseUrl);
    return WebClient.builder().baseUrl(baseUrl).build();
    }
}

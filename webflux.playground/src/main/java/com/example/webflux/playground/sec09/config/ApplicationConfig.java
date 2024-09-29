package com.example.webflux.playground.sec09.config;

import com.example.webflux.playground.sec09.dto.ProductResponse;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import reactor.core.publisher.Sinks;

@Configuration
public class ApplicationConfig {
    @Bean
    public Sinks.Many<ProductResponse> sink() {
        return Sinks.many().replay().limit(1);
    }
}

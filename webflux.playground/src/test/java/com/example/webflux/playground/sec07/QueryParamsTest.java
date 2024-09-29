package com.example.webflux.playground.sec07;

import com.example.webflux.playground.sec07.dto.CalculatorResponse;
import com.example.webflux.playground.sec07.dto.Product;
import org.junit.jupiter.api.Test;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.test.StepVerifier;

import java.util.Map;

public class QueryParamsTest extends AbstractWebClient{
    private final WebClient webClient = createWebClient();


    @Test
    void uriBuilderVariables(){
        var path = "/lec06/calculator";
        var query= "first={first}&second={second}&operation={operation}";
        webClient.get()
                .uri(uriBuilder -> uriBuilder.path(path).query(query).build(10,20,"+"))
                .retrieve()
                .bodyToMono(CalculatorResponse.class)
                .doOnNext(print())
                .then()
                .as(StepVerifier::create)
                .verifyComplete();
    }

    @Test
    void uriBuilderMap(){
        var path = "/lec06/calculator";
        var query= "first={first}&second={second}&operation={operation}";
        var map = Map.of(
                "first",10,
                "second",20,
                "operation","+"
        );
        webClient.get()
                .uri(uriBuilder -> uriBuilder.path(path).query(query).build(map))
                .retrieve()
                .bodyToMono(CalculatorResponse.class)
                .doOnNext(print())
                .then()
                .as(StepVerifier::create)
                .verifyComplete();
    }
}

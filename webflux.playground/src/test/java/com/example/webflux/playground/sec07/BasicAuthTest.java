package com.example.webflux.playground.sec07;

import com.example.webflux.playground.sec07.dto.CalculatorResponse;
import com.example.webflux.playground.sec07.dto.Product;
import org.junit.jupiter.api.Test;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.test.StepVerifier;

public class BasicAuthTest extends AbstractWebClient{
    private final WebClient webClient = createWebClient(
            h -> h.defaultHeaders(x->x.setBasicAuth("java","secret"))
    );

    @Test
    void basicAuthTest(){
        webClient.get()
                .uri("/lec07/product/{id}",1)
                .retrieve()
                .bodyToMono(Product.class)
                .doOnNext(print())
                .then()
                .as(StepVerifier::create)
                .verifyComplete();
    }
}

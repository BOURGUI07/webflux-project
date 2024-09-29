package com.example.webflux.playground.sec07;

import com.example.webflux.playground.sec07.dto.CalculatorResponse;
import org.junit.jupiter.api.Test;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.test.StepVerifier;

public class BearerAuthTest extends AbstractWebClient{
    private final WebClient webClient = createWebClient(
            h->h.defaultHeaders(x->x.setBearerAuth("eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9"))
    );

    @Test
    void bearerAuth(){
        webClient.get()
                .uri("/lec08/product/{id}",1)
                .retrieve()
                .bodyToMono(CalculatorResponse.class)
                .doOnNext(print())
                .then()
                .as(StepVerifier::create)
                .verifyComplete();
    }
}

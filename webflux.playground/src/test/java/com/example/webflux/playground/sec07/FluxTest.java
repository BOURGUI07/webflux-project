package com.example.webflux.playground.sec07;

import com.example.webflux.playground.sec07.dto.Product;
import org.junit.jupiter.api.Test;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.test.StepVerifier;

import java.time.Duration;

public class FluxTest extends AbstractWebClient{
    private WebClient client = createWebClient();

    @Test
    void streamingResponse() throws InterruptedException {
        client.get()
                .uri("/lec02/product/stream")
                .retrieve()
                .bodyToFlux(Product.class)
                .take(Duration.ofSeconds(3))
                .doOnNext(print())
                .then()
                .as(StepVerifier::create)
                .verifyComplete();
    }
}

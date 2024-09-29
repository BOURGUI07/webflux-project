package com.example.webflux.playground.sec07;

import com.example.webflux.playground.sec07.dto.Product;
import org.junit.jupiter.api.Test;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.time.Duration;

public class PostTest extends AbstractWebClient{
    private final WebClient client = createWebClient();

    @Test
    public void postBodyValue(){
        client.post()
                .uri("/lec03/product")
                .bodyValue(new Product(null, "ProductX", 45))
                .retrieve()
                .bodyToMono(Product.class)
                .doOnNext(print())
                .then()
                .as(StepVerifier::create)
                .verifyComplete();
    }

    @Test
    public void postBody(){
        var mono = Mono.fromSupplier(() -> new Product(null, "ProductX", 45))
                .delayElement(Duration.ofSeconds(1));
        client.post()
                .uri("/lec03/product")
                .body(mono, Product.class)
                .retrieve()
                .bodyToMono(Product.class)
                .doOnNext(print())
                .then()
                .as(StepVerifier::create)
                .verifyComplete();
    }
}

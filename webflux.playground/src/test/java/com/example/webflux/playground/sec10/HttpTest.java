package com.example.webflux.playground.sec10;

import com.example.webflux.playground.sec02.AbstractTest;
import com.example.webflux.playground.sec10.dto.Product;
import org.junit.jupiter.api.Test;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.netty.http.HttpProtocol;
import reactor.netty.http.client.HttpClient;
import reactor.netty.resources.ConnectionProvider;
import reactor.test.StepVerifier;

public class HttpTest extends AbstractWebClient {
    private final WebClient webClient = createWebClient(b->
    {
        var poolSize = 1;
        var provider = ConnectionProvider.builder("youness")
                .lifo()
                .maxConnections(poolSize)
                .build();
        var httpClient = HttpClient.create(provider)
                .protocol(HttpProtocol.H2C)
                .compress(true)
                .keepAlive(true);
        b.clientConnector(new ReactorClientHttpConnector(httpClient));
    });

    private Mono<Product> getProduct(int id) {
        return webClient.get().uri("/product/{id}", id)
                .retrieve()
                .bodyToMono(Product.class);
    }

    @Test
    void testConcurrentTests(){
        // with max=250, it will take 5 secs to get all 250 products
        // with max=260, it will take more than 5 secs since the max queue size for
        // the flatmap is 256.
        // to process 260 items in 5secs
        // first 500 requests will be sent in 5secs
        // but more than that, it will take more than 10 secs
        // because the WebClient sends maximum 500 connections
        var max = 3;
        Flux.range(1,max)
                .flatMap(this::getProduct,max)
                .collectList()
                .as(StepVerifier::create)
                .expectNextCount(max)
                .verifyComplete();

    }
}

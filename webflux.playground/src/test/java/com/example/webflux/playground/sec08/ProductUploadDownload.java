package com.example.webflux.playground.sec08;

import com.example.webflux.playground.sec08.dto.ProductRequest;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import java.time.Duration;

@Slf4j
public class ProductUploadDownload {
    private final ProductClient client = new ProductClient();

    @Test
    void test(){
        var flux = Flux.just(new ProductRequest("product-1",45))
                .delayElements(Duration.ofSeconds(10));
        client.uploadProducts(flux)
        .doOnNext(p-> log.info("RECEIVED: {}",p))
                .then()
                .as(StepVerifier::create)
                .verifyComplete();
    }

    @Test
    void upload(){
        var flux = Flux.range(1,10)
                .map(x->new ProductRequest("product-"+x,x+45))
                .delayElements(Duration.ofSeconds(2));
        client.uploadProducts(flux)
                .doOnNext(p-> log.info("RECEIVED: {}",p))
                .then()
                .as(StepVerifier::create)
                .verifyComplete();
    }

    @Test
    void download(){
        client.downloadProducts()
                .doOnNext(p-> log.info("RECEIVED: {}",p))
                .then()
                .as(StepVerifier::create)
                .verifyComplete();
    }
}

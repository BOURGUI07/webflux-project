package com.example.webflux.playground.sec09.service;

import com.example.webflux.playground.sec09.dto.ProductRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.concurrent.ThreadLocalRandom;

@Service
@RequiredArgsConstructor
public class DataSetupService implements CommandLineRunner {
    private final ProductService productService;
    @Override
    public void run(String... args) throws Exception {
        Flux.range(1, 100)
                .delayElements(Duration.ofSeconds(1))
                .map(x-> new ProductRequest("Product-"+x, ThreadLocalRandom.current().nextInt(1,100)))
                .flatMap(x->productService.save(Mono.just(x)))
                .subscribe();
    }
}

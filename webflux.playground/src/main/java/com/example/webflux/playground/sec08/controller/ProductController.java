package com.example.webflux.playground.sec08.controller;

import com.example.webflux.playground.sec08.dto.ProductRequest;
import com.example.webflux.playground.sec08.dto.ProductResponse;
import com.example.webflux.playground.sec08.dto.UploadResponse;
import com.example.webflux.playground.sec08.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

@RestController
@RequestMapping("/api/products")
@Slf4j
@RequiredArgsConstructor
public class ProductController {
    private final ProductService service;

    @PostMapping(value="/upload", consumes = MediaType.APPLICATION_NDJSON_VALUE)
    public Mono<UploadResponse> uploadProducts(@RequestBody Flux<ProductRequest> products){
        log.info("Uploading products");
        return service.saveProducts(products)
                .doOnNext(response -> log.info("Product Response Received: {}", response))
                .then(service.count())
                .map(x->new UploadResponse(UUID.randomUUID(),x));
    }

    @GetMapping(value="/download", produces = MediaType.APPLICATION_NDJSON_VALUE)
    public Flux<ProductResponse> downloadProducts(){
        log.info("Downloading products");
        return service.findAll()
                .doOnNext(response -> log.info("Product Download Received: {}", response));
    }
}

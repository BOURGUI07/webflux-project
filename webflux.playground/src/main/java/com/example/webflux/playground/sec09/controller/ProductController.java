package com.example.webflux.playground.sec09.controller;

import com.example.webflux.playground.sec09.dto.ProductRequest;
import com.example.webflux.playground.sec09.dto.ProductResponse;
import com.example.webflux.playground.sec09.dto.UploadResponse;
import com.example.webflux.playground.sec09.service.ProductService;
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

    @PostMapping
    public Mono<ProductResponse> createProduct(@RequestBody Mono<ProductRequest> request) {
        return service.save(request);
    }

    @GetMapping(value = "/stream/{maxPrice}",produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<ProductResponse> getProducts(@PathVariable Integer maxPrice) {
        return service.productStream()
                .filter(x->x.price()<=maxPrice);
    }
}

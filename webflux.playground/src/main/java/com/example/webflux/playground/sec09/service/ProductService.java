package com.example.webflux.playground.sec09.service;

import com.example.webflux.playground.sec09.dto.ProductRequest;
import com.example.webflux.playground.sec09.dto.ProductResponse;
import com.example.webflux.playground.sec09.mapper.ProductMapper;
import com.example.webflux.playground.sec09.repo.ProductRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.publisher.Sinks;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepo repo;
    private final ProductMapper mapper;
    private final Sinks.Many<ProductResponse> sink;

    public Mono<ProductResponse> save(Mono<ProductRequest> request) {
        return request.map(mapper::toEntity)
                .flatMap(repo::save)
                .map(mapper::toResponse)
                .doOnNext(sink::tryEmitNext);
    }

    public Flux<ProductResponse> productStream() {
        return sink.asFlux();
    }
}

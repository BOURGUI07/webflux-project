package com.example.webflux.playground.sec08.service;

import com.example.webflux.playground.sec08.dto.ProductRequest;
import com.example.webflux.playground.sec08.dto.ProductResponse;
import com.example.webflux.playground.sec08.mapper.ProductMapper;
import com.example.webflux.playground.sec08.repo.ProductRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepo repo;
    private final ProductMapper mapper;

    public Flux<ProductResponse> saveProducts(Flux<ProductRequest> products) {
        return products.map(mapper::toEntity)
                .as(repo::saveAll)
                .map(mapper::toResponse);
    }

    public Mono<Long> count() {
        return repo.count();
    }

    public Mono<ProductResponse> findById(Integer id){
        return repo.findById(id)
                .map(mapper::toResponse);
    }

    public Flux<ProductResponse> findAll(){
        return repo.findAll()
                .map(mapper::toResponse);
    }

    public Mono<ProductResponse> save(Mono<ProductRequest> request) {
        return request.map(mapper::toEntity)
                .flatMap(repo::save)
                .map(mapper::toResponse);
    }

    public Mono<ProductResponse> update(Integer id, Mono<ProductRequest> request) {
        return repo.findById(id)
                .zipWith(request)
                .flatMap(x->{
                    var req = x.getT2();
                    var entity = x.getT1().setDescription(req.description()).setPrice(req.price());
                    return repo.save(entity);
                })
                .map(mapper::toResponse);
    }


    public Mono<Void> delete(Integer id) {
        return repo.deleteById(id);
    }
}

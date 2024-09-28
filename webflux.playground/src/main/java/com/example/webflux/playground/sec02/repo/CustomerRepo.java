package com.example.webflux.playground.sec02.repo;

import com.example.webflux.playground.sec02.entity.Customer;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public interface CustomerRepo extends ReactiveCrudRepository<Customer, Integer> {
    Mono<Customer> findByName(String name);
    Flux<Customer> findByEmailEndingWith(String email);
}

package com.example.webflux.playground.sec09.repo;

import com.example.webflux.playground.sec09.entity.Product;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepo extends ReactiveCrudRepository<Product,Integer> {
}

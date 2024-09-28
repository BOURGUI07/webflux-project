package com.example.webflux.playground.sec02;

import com.example.webflux.playground.sec02.repo.ProductRepo;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import reactor.test.StepVerifier;

public class ProductRepoTest extends AbstractTest {
    @Autowired
    private ProductRepo repo;

    @Test
    void priceBetween(){
        repo.findByPriceBetween(500,1000)
                .as(StepVerifier::create)
                .expectNextCount(3)
                .verifyComplete();
    }

    @Test
    void pageable(){
        var pageRequest = PageRequest.of(0,2, Sort.by("price").ascending());
        repo.findBy(pageRequest)
                .as(StepVerifier::create)
                .assertNext(c-> Assertions.assertTrue(c.getDescription().equals("apple tv")))
                .thenCancel()
                .verify();
    }
}

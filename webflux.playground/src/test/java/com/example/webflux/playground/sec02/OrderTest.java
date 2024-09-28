package com.example.webflux.playground.sec02;

import com.example.webflux.playground.sec02.repo.OrderRepo;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import reactor.test.StepVerifier;

public class OrderTest extends AbstractTest {

    @Autowired
    private OrderRepo repo;

    @Test
    void findProductsOrderedByCustomer(){
        repo.findProductsOrderedByCustomer("sam1")
                .as(StepVerifier::create)
                .expectNextCount(2)
                .verifyComplete();

        repo.findProductsOrderedByCustomer("sam1")
                .as(StepVerifier::create)
                .assertNext(product -> Assertions.assertEquals(1,product.getId()))
                .assertNext(product -> Assertions.assertEquals(2,product.getId()))
                .verifyComplete();
    }

    @Test
    void findOrderDetailsByProductName(){
        repo.findOrderDetailsByProductName("iphone 20")
                .as(StepVerifier::create)
                .expectNextCount(2)
                .verifyComplete();
        repo.findOrderDetailsByProductName("iphone 20")
                .as(StepVerifier::create)
                .assertNext(od-> Assertions.assertEquals(975,od.amount()))
                .assertNext(od-> Assertions.assertEquals(950,od.amount()))
                .verifyComplete();
    }
}

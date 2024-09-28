package com.example.webflux.playground.sec02;


import com.example.webflux.playground.sec02.entity.Customer;
import com.example.webflux.playground.sec02.repo.CustomerRepo;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import reactor.test.StepVerifier;

import java.util.Objects;

public class CustomerRepoTest extends AbstractTest {
    @Autowired
    private CustomerRepo customerRepo;

    @Test
    void findAll(){
        StepVerifier.create(customerRepo.findAll())
                .expectNextCount(10)
                .thenConsumeWhile(c -> Objects.nonNull(c.getId()))
                .verifyComplete();
        /*
            The above is same as below
         */
        customerRepo.findAll()
                .as(StepVerifier::create)
                .expectNextCount(10)
                .thenConsumeWhile(c -> Objects.nonNull(c.getId()))
                .verifyComplete();
    }

    @Test
    void findById(){
        StepVerifier.create(customerRepo.findById(11))
                .verifyComplete();
        StepVerifier.create(customerRepo.findById(1))
                .assertNext(c-> Assertions.assertNotNull(c.getId()))
                .verifyComplete();
        StepVerifier.create(customerRepo.findById(2))
                .consumeNextWith(c->{
                    Assertions.assertEquals("mike", c.getName());
                    Assertions.assertEquals("mike@gmail.com",c.getEmail());
                })
                .verifyComplete();
    }


    @Test
    void findByName(){
        StepVerifier.create(customerRepo.findByName("liam"))
                .assertNext(c-> Assertions.assertEquals("liam@example.com",c.getEmail()))
                .verifyComplete();
    }

    @Test
    void findByEmailEndingWith(){
        StepVerifier.create(customerRepo.findByEmailEndingWith("example.com"))
                .expectNextCount(7)
                .verifyComplete();

        StepVerifier.create(customerRepo.findByEmailEndingWith("example.com").collectList())
                .assertNext(list -> Assertions.assertTrue(list.stream().allMatch(c-> c.getEmail().contains("@example.com"))))
                .verifyComplete();

        StepVerifier.create(customerRepo.findByEmailEndingWith("gmail.com"))
                .assertNext(c->Assertions.assertEquals("sam@gmail.com",c.getEmail()))
                .assertNext(c->Assertions.assertEquals("mike@gmail.com",c.getEmail()))
                .assertNext(c->Assertions.assertEquals("jake@gmail.com",c.getEmail()))
                .verifyComplete();
    }

    @Test
    void insert(){
        var customer = new Customer().setEmail("youness@gmail.com").setName("youness");
        StepVerifier.create(customerRepo.save(customer))
                .assertNext(c -> Assertions.assertNotNull(c.getId()))
                .verifyComplete();

        StepVerifier.create(customerRepo.count())
                .assertNext(x-> Assertions.assertEquals(11, (long) x))
                .verifyComplete();

        customerRepo.deleteById(11)
                .then(customerRepo.count())
                .as(StepVerifier::create)
                .expectNext(10L)
                .verifyComplete();
    }

    @Test
    void update(){
        customerRepo.findByName("sam")
                .doOnNext(c->c.setName("sam1"))
                .flatMap(customerRepo::save)
                .then(customerRepo.findByName("sam1"))
                .as(StepVerifier::create)
                .assertNext(c->Assertions.assertEquals("sam@gmail.com", c.getEmail()))
                .verifyComplete();
    }


}

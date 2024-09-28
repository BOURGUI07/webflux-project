package com.example.webflux.playground.sec04;

import com.example.webflux.playground.sec02.dto.request.CustomerRequest;
import com.example.webflux.playground.sec02.dto.response.CustomerResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.WebTestClient;

import static org.hamcrest.Matchers.hasSize;

@SpringBootTest
@AutoConfigureWebTestClient
public class CustomerServiceTest {
    @Autowired
    private WebTestClient client;


    @Test
    void findById(){
        client.get()
                .uri("/api/customers/19")
                .exchange()
                .expectStatus().is4xxClientError()
                .expectBody()
                .jsonPath("$.title").isEqualTo("Customer not found")
                .jsonPath("$.detail").isEqualTo("Customer with id 19 not found")
                .jsonPath("$.status").isEqualTo(404);
    }

    @Test
    void delete(){
        client.delete()
                .uri("/api/customers/19")
                .exchange()
                .expectStatus().is4xxClientError()
                .expectBody()
                .jsonPath("$.title").isEqualTo("Customer not found")
                .jsonPath("$.detail").isEqualTo("Customer with id 19 not found")
                .jsonPath("$.status").isEqualTo(404);
    }

    @Test
    void createAndUpdate(){
        var request = new CustomerRequest(null,"sam@gmail.com");
        client.post()
                .uri("/api/customers")
                .bodyValue(request)
                .exchange()
                .expectStatus().is4xxClientError()
                .expectBody()
                .jsonPath("$.title").isEqualTo("Invalid input")
                .jsonPath("$.detail").isEqualTo("Name is required")
                .jsonPath("$.status").isEqualTo(400);
        client.put()
                .uri("/api/customers/2")
                .bodyValue(request)
                .exchange()
                .expectStatus().is4xxClientError()
                .expectBody()
                .jsonPath("$.title").isEqualTo("Invalid input")
                .jsonPath("$.detail").isEqualTo("Name is required")
                .jsonPath("$.status").isEqualTo(400);

        var request1 = new CustomerRequest("sam","sam.gmail.com");
        client.post()
                .uri("/api/customers")
                .bodyValue(request1)
                .exchange()
                .expectStatus().is4xxClientError()
                .expectBody()
                .jsonPath("$.title").isEqualTo("Invalid input")
                .jsonPath("$.detail").isEqualTo("Email is Invalid")
                .jsonPath("$.status").isEqualTo(400);
        client.put()
                .uri("/api/customers/2")
                .bodyValue(request1)
                .exchange()
                .expectStatus().is4xxClientError()
                .expectBody()
                .jsonPath("$.title").isEqualTo("Invalid input")
                .jsonPath("$.detail").isEqualTo("Email is Invalid")
                .jsonPath("$.status").isEqualTo(400);
    }



}

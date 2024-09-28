package com.example.webflux.playground.sec02;

import com.example.webflux.playground.sec02.dto.request.CustomerRequest;
import com.example.webflux.playground.sec02.dto.response.CustomerResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;

import static org.hamcrest.Matchers.hasSize;

@SpringBootTest
@AutoConfigureWebTestClient
public class CustomerServiceTest {
    @Autowired
    private WebTestClient client;

    @Test
    void findAll(){
        client.get()
                .uri("/api/customers")
                .exchange()
                .expectStatus().is2xxSuccessful()
                .expectBodyList(CustomerResponse.class)
                .hasSize(10);

    }

    @Test
    void paginated(){
        client.get()
                .uri("/api/customers/paginated?page=3&size=2")
                .exchange()
                .expectStatus().is2xxSuccessful()
                .expectBody()
                .jsonPath("$.totalCount").isEqualTo(10)
                .jsonPath("$.customers").isArray()
                .jsonPath("$.customers").value(hasSize(2))
                .jsonPath("$.customers[0].id").isEqualTo(7)
                .jsonPath("$.customers[1].id").isEqualTo(8);
    }

    @Test
    void findById(){
        client.get()
                .uri("/api/customers/1")
                .exchange()
                .expectStatus().is2xxSuccessful()
                .expectBody()
                .jsonPath("$.id").isEqualTo(1)
                .jsonPath("$.name").isEqualTo("sam1");
    }

    @Test
    void createAndDelete(){
        var request = new CustomerRequest("john","john@gmail.com");
        client.post()
                .uri("/api/customers")
                .bodyValue(request)
                .exchange()
                .expectStatus().is2xxSuccessful()
                .expectBody()
                .jsonPath("$.id").isEqualTo(16)
                .jsonPath("$.name").isEqualTo("john")
                .jsonPath("$.email").isEqualTo("john@gmail.com");
        client.delete()
                .uri("/api/customers/16")
                .exchange()
                .expectStatus().is2xxSuccessful();
    }

    @Test
    void update(){
        var request = new CustomerRequest("sam","sam@gmail.com");
        client.put()
                .uri("/api/customers/1")
                .bodyValue(request)
                .exchange()
                .expectStatus().is2xxSuccessful()
                .expectBody()
                .jsonPath("$.id").isEqualTo(1)
                .jsonPath("$.name").isEqualTo("sam")
                .jsonPath("$.email").isEqualTo("sam@gmail.com");
    }

    @Test
    void notFound(){
        client.get()
                .uri("/api/customers/17")
                .exchange()
                .expectStatus().isNotFound()
                .expectBody().isEmpty();
        var request = new CustomerRequest("sam","sam@gmail.com");
        client.put()
                .uri("/api/customers/17")
                .bodyValue(request)
                .exchange()
                .expectStatus().isNotFound()
                .expectBody().isEmpty();

        client.delete()
                .uri("/api/customers/17")
                .exchange()
                .expectStatus().isNotFound()
                .expectBody().isEmpty();
    }

}

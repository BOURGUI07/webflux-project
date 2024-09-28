package com.example.webflux.playground.sec05;


import com.example.webflux.playground.sec05.dto.request.CustomerRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.WebTestClient;
@SpringBootTest
@AutoConfigureWebTestClient
public class CustomerServiceTest{

    @Autowired
    private WebTestClient client;

    @Test
    void noTokenUnauthorized(){
        client.get()
                .uri("/api/customers")
                .exchange()
                .expectHeader()
                .doesNotExist("auth-token")
                .expectStatus()
                .isUnauthorized();
    }

    @Test
    void invalidTokenUnauthorized(){
        client.get()
                .uri("/api/customers")
                .header("auth-token", "invalid")
                .exchange()
                .expectStatus()
                .isUnauthorized();


    }

    @Test
    void validTokenGETOk(){
        client.get()
                .uri("/api/customers")
                .header("auth-token", "secret321","secret456")
                .exchange()
                .expectStatus()
                .isOk();


    }

    @Test
    void validTokenNotGetForbidden(){
        client.post()
                .uri("/api/customers")
                .header("auth-token", "secret321")
                .exchange()
                .expectStatus()
                .isForbidden();


    }
    @Test
    void validTokenNotGetOk(){
        var request = new CustomerRequest("new","new@gmail.com");
        client.post()
                .uri("/api/customers")
                .bodyValue(request)
                .header("auth-token", "secret456")
                .exchange()
                .expectStatus()
                .isOk();



    }
}

package com.example.webflux.playground.sec09;

import com.example.webflux.playground.sec09.dto.ProductResponse;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.test.StepVerifier;

@SpringBootTest
@AutoConfigureWebTestClient
@Slf4j
public class ServerSentEventsTest {
    @Autowired
    private WebTestClient client;
    @Test
    public void testServerSentEvents(){
        client.get()
                .uri("/api/products/stream/{maxPrice}",80)
                .accept(MediaType.TEXT_EVENT_STREAM)
                .exchange()
                .expectStatus().isOk()
                .returnResult(ProductResponse.class)
                .getResponseBody()
                .take(3)
                .doOnNext(x->log.info("RECEIVED: {}", x))
                .collectList()
                .as(StepVerifier::create)
                .assertNext(list->{
                    Assertions.assertEquals(3,list.size());
                    Assertions.assertTrue(list.stream().allMatch(p->p.price()<=80));
                })
                .verifyComplete();


    }
}

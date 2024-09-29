package com.example.webflux.playground.sec07;


import com.example.webflux.playground.sec07.dto.Product;
import org.junit.jupiter.api.Test;
import org.springframework.web.reactive.function.client.WebClient;

import java.time.Duration;
import java.util.Map;

public class MonoTest extends AbstractWebClient {
    private final WebClient client = createWebClient();

    @Test
    void simpleGet() throws InterruptedException {
        var map = Map.of("lec","lec01",
                        "id",1);
        client.get()
          //      .uri("/{lec}/product/{id}",map)
         //       .uri("/{lec}/product/{id}","lec01",1)
                .uri("/lec01/product/1")
                .retrieve()
                .bodyToMono(Product.class)
                .doOnNext(print())
                .subscribe();
        Thread.sleep(Duration.ofSeconds(2));
    }

    @Test
    void concurrentGet() throws InterruptedException {
        for(int i=1;i<=5;i++){
            client.get()
                    .uri("/lec01/product/1")
                    .retrieve()
                    .bodyToMono(Product.class)
                    .doOnNext(print())
                    .subscribe();
        }
        Thread.sleep(Duration.ofSeconds(2));
        /*
            It will get all products in one sec
         */
    }


}

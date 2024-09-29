package com.example.webflux.playground.sec07;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.function.Consumer;

@Slf4j
public abstract class AbstractWebClient {
    protected <T> Consumer<T> print(){
        return item -> log.info("RECEIVED: {}" , item);
    }
    protected WebClient createWebClient() {
        return createWebClient(b->{});
    }

    protected WebClient createWebClient(Consumer<WebClient.Builder> consumer) {
        var builder = WebClient.builder()
                .baseUrl("http://localhost:7070/demo02");
        consumer.accept(builder);
        return builder.build();
    }
}

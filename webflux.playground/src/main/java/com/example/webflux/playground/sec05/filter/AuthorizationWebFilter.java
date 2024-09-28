package com.example.webflux.playground.sec05.filter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

@Service
@Order(2)
@Slf4j
public class AuthorizationWebFilter implements WebFilter {
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        var category = exchange.getAttributeOrDefault("category",Category.STANDARD);
        return switch (category) {
            case STANDARD -> standard(exchange,chain);
            case PRIME -> prime(exchange,chain);
        };
    }

    public Mono<Void> prime(ServerWebExchange exchange, WebFilterChain chain) {
        return chain.filter(exchange);
    }

    public Mono<Void> standard(ServerWebExchange exchange, WebFilterChain chain) {
        log.info("STANDARD METHOD INVOKED");
        var method = exchange.getRequest().getMethod();
        log.info("METHOD INVOKED : " + method);
        var isGet = HttpMethod.GET.equals(method);
        if(isGet) {
            log.info("METHOD INVOKED IS GET");
            return chain.filter(exchange);
        }else{
            log.info("METHOD INVOKED IS NOT GET");
            return Mono.fromRunnable(() -> exchange.getResponse().setStatusCode(HttpStatus.FORBIDDEN));
        }

    }
}

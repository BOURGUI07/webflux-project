package com.example.webflux.playground.sec05.filter;

import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

import java.util.Map;
import java.util.Objects;

@Service
@Order(1)
public class AuthenticationWebFilter implements WebFilter {
    private static final Map<String,Category> map = Map.of(
            "secret321",Category.STANDARD,
            "secret456",Category.PRIME
    );
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        var token = exchange.getRequest().getHeaders().getFirst("auth-token");
        if(Objects.nonNull(token) && map.containsKey(token)){
            exchange.getAttributes().put("category",map.get(token));
            return chain.filter(exchange);
        }
        return Mono.fromRunnable(() -> exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED));

    }
}

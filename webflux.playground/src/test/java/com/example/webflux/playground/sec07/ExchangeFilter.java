package com.example.webflux.playground.sec07;

import com.example.webflux.playground.sec07.dto.CalculatorResponse;
import com.example.webflux.playground.sec07.dto.Product;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.web.reactive.function.client.ClientRequest;
import org.springframework.web.reactive.function.client.ExchangeFilterFunction;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.test.StepVerifier;

import java.util.UUID;

@Slf4j
public class ExchangeFilter extends AbstractWebClient {
    private final WebClient webClient = createWebClient(h->
           h.filter(tokenGenerator()));

    @Test
    void exchangeFilter() {
        for(int i=1;i<=10;i++){
            webClient.get()
                    .uri("/lec09/product/{id}",i)
                    .attribute("enable-logging",i%2==0)
                    .retrieve()
                    .bodyToMono(Product.class)
                    .doOnNext(print())
                    .then()
                    .as(StepVerifier::create)
                    .verifyComplete();
        }
    }

    private ExchangeFilterFunction tokenGenerator(){
        return (request, next) -> {
                var isEnabled =(Boolean) request.attributes().getOrDefault("enable-logging", false);
                if(isEnabled){
                    var method = request.method();
                    log.info("HTTP METHOD: {}", method);
                    var url = request.url();
                    log.info("URL: {}", url);
                }
                var token = UUID.randomUUID().toString().replace("-", "");
                log.info("GENERATED TOKEN: {}", token);
                 var modifiedRequest = ClientRequest.from(request).headers(h->h.setBearerAuth(token)).build();
                return next.exchange(modifiedRequest);
        };
    }



}

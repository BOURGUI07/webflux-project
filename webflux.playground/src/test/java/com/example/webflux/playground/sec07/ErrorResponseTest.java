package com.example.webflux.playground.sec07;

import com.example.webflux.playground.sec02.AbstractTest;
import com.example.webflux.playground.sec07.dto.CalculatorResponse;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.http.ProblemDetail;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

@Slf4j
public class ErrorResponseTest extends AbstractWebClient {
    private final WebClient webClient = createWebClient();

    @Test
    void test(){
        webClient.get()
                .uri("/lec05/calculator/{first}/{second}",1,4)
                .header("operation","+")
                .retrieve()
                .bodyToMono(CalculatorResponse.class)
                .doOnNext(print())
                .then()
                .as(StepVerifier::create)
                .verifyComplete();
    }

    @Test
    void test1(){
        webClient.get()
                .uri("/lec05/calculator/{first}/{second}",1,4)
                .header("operation","@")
                .retrieve()
                .bodyToMono(CalculatorResponse.class)
                .doOnError(WebClientResponseException.class,ex -> log.info("DETAIL: {}",ex.getResponseBodyAs(ProblemDetail.class)))
         //       .onErrorReturn(new CalculatorResponse(0,0,null,0.0))
                .onErrorReturn(WebClientResponseException.BadRequest.class,new CalculatorResponse(0,0,null,0.0))
                .doOnNext(print())
                .then()
                .as(StepVerifier::create)
                .verifyComplete();
    }

    @Test
    void exchange(){
        webClient.get()
                .uri("/lec05/calculator/{first}/{second}",1,4)
                .header("operation","@")
                .exchangeToMono(this::decode)
                .doOnNext(print())
                .then()
                .as(StepVerifier::create)
                .verifyComplete();
    }

    private Mono<CalculatorResponse> decode(ClientResponse response){
        //response.cookies()
        //response.headers()
        log.info("STATUS CODE: {}", response.statusCode());
        if(response.statusCode().isError()){
            return response.bodyToMono(ProblemDetail.class)
                    .doOnNext(pd->log.info("Problem Detail: {}",pd))
                    .then(Mono.empty());
        }
        return response.bodyToMono(CalculatorResponse.class);
    }
}

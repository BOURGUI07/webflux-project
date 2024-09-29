package com.example.webflux.playground.sec08;

import com.example.webflux.playground.sec08.dto.ProductRequest;
import com.example.webflux.playground.sec08.dto.ProductResponse;
import com.example.webflux.playground.sec08.dto.UploadResponse;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class ProductClient {
    private final WebClient webClient = WebClient.builder()
            .baseUrl("http://localhost:8080/api/products")
            .build();


    public Mono<UploadResponse> uploadProducts(Flux<ProductRequest> products){
        return webClient.post()
                .uri("/upload")
                .contentType(MediaType.APPLICATION_NDJSON)
                .body(products, ProductRequest.class)
                .retrieve()
                .bodyToMono(UploadResponse.class);
    }

    public Flux<ProductResponse> downloadProducts(){
        return webClient.get()
                .uri("/download")
                .accept(MediaType.APPLICATION_NDJSON)
                .retrieve()
                .bodyToFlux(ProductResponse.class);
    }
}

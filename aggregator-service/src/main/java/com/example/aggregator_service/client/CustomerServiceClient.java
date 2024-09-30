package com.example.aggregator_service.client;

import com.example.aggregator_service.dto.CustomerInformation;
import com.example.aggregator_service.dto.StockTradeRequest;
import com.example.aggregator_service.dto.StockTradeResponse;
import com.example.aggregator_service.exceptions.ApplicationExceptions;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ProblemDetail;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;

@Slf4j
@RequiredArgsConstructor
public class CustomerServiceClient {
    private final WebClient webClient;

    public Mono<CustomerInformation> getCustomerInformation(Integer customerId) {
        return webClient
                .get()
                .uri("/{customerId}", customerId)
                .retrieve()
                .bodyToMono(CustomerInformation.class)
                .onErrorResume(WebClientResponseException.NotFound.class, ex -> ApplicationExceptions.customerNoFoundException(customerId));
    }

    public Mono<StockTradeResponse> trade(Integer customerId, StockTradeRequest request) {
        return webClient.post()
                .uri("/{customerId}/trade", customerId)
                .bodyValue(request)
                .retrieve()
                .bodyToMono(StockTradeResponse.class)
                .onErrorResume(WebClientResponseException.NotFound.class, ex -> ApplicationExceptions.customerNoFoundException(customerId))
                .onErrorResume(WebClientResponseException.BadRequest.class, this::handleException);
    }

    private <T> Mono<T> handleException(WebClientResponseException.BadRequest badRequest) {
        var pd=  badRequest.getResponseBodyAs(ProblemDetail.class);
        var message=  pd==null? badRequest.getMessage():pd.getDetail();
        return ApplicationExceptions.invalidTradeRequestException(message);
    }

}
package com.example.aggregator_service.exceptions;

import reactor.core.publisher.Mono;

public class ApplicationExceptions {
    public static <T> Mono<T> customerNoFoundException(Integer id){
        return Mono.error(new CustomerNotFoundException(id));
    }

    public static <T> Mono<T> invalidTradeRequestException(String message){
        return Mono.error(new InvalidTradeRequestException(message));
    }

    public static <T> Mono<T> tickerIsMissing(){
        return Mono.error(new InvalidTradeRequestException("Ticker is required"));
    }

    public static <T> Mono<T> tradeActionIsMissing(){
        return Mono.error(new InvalidTradeRequestException("Trade Action is required"));
    }

    public static <T> Mono<T> invalidQuantity(){
        return Mono.error(new InvalidTradeRequestException("Quantity should be positive"));
    }
}

package com.example.customer_service.exceptions;

import reactor.core.publisher.Mono;

public class ApplicationExceptions {
    public static <T> Mono<T> customerNoFoundException(Integer id){
        return Mono.error(new CustomerNotFoundException(id));
    }

    public static <T> Mono<T> notEnoughBalance(Integer id){
        return Mono.error(new NotEnoughBalanceException(id));
    }

    public static <T> Mono<T> notEnoughShares(Integer id){
        return Mono.error(new NotEnoughSharesException(id));
    }
}

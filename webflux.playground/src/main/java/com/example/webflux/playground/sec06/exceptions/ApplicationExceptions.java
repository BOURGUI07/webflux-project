package com.example.webflux.playground.sec06.exceptions;

import reactor.core.publisher.Mono;

public class ApplicationExceptions {
    public static <T> Mono<T> customerNoFoundException(Integer id){
        return Mono.error(new CustomerNotFoundException(id));
    }

    public static <T> Mono<T> missingName(){
        return Mono.error(new InvalidInputException("Name is required"));
    }

    public static <T> Mono<T> invalidEmail(){
        return Mono.error(new InvalidInputException("Email is Invalid"));
    }


}

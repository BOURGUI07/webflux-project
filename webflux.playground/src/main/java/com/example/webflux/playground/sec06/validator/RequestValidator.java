package com.example.webflux.playground.sec06.validator;

import com.example.webflux.playground.sec06.dto.request.CustomerRequest;
import com.example.webflux.playground.sec06.exceptions.ApplicationExceptions;
import reactor.core.publisher.Mono;

import java.util.Objects;
import java.util.function.Predicate;
import java.util.function.UnaryOperator;

public class RequestValidator {
    public static Predicate<CustomerRequest> hasName(){
        return request -> Objects.nonNull(request.name());
    }

    public static Predicate<CustomerRequest> hasValidEmail(){
        return request -> Objects.nonNull(request.email())
                && request.email().contains("@");
    }

    public static UnaryOperator<Mono<CustomerRequest>> isValid(){
        return mono -> mono.filter(hasName())
                .switchIfEmpty(ApplicationExceptions.missingName())
                .filter(hasValidEmail())
                .switchIfEmpty(ApplicationExceptions.invalidEmail());
    }
}

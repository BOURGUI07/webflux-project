package com.example.webflux.playground.sec06.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class CustomerNotFoundException extends RuntimeException {
    public CustomerNotFoundException(Integer customerId) {
        super("Customer with id " + customerId + " not found");
    }
}

package com.example.customer_service.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class NotEnoughSharesException extends RuntimeException {
    public NotEnoughSharesException(Integer customerId) {
        super("Customer " + customerId + " is not enough shares");
    }
}

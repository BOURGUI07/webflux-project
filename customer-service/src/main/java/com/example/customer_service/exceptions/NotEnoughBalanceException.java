package com.example.customer_service.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class NotEnoughBalanceException extends RuntimeException {
    public NotEnoughBalanceException(Integer customerId) {
        super("Customer with Id: " + customerId + " doesn't have enough balance");
    }
}

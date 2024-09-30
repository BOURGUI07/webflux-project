package com.example.customer_service.advice;

import com.example.customer_service.exceptions.CustomerNotFoundException;
import com.example.customer_service.exceptions.NotEnoughBalanceException;
import com.example.customer_service.exceptions.NotEnoughSharesException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.net.URI;
import java.util.function.Consumer;

@ControllerAdvice
public class ApplicationExceptionHandler {

    @ExceptionHandler(CustomerNotFoundException.class)
    public ProblemDetail handleCustomerNotFoundException(CustomerNotFoundException ex) {
        return build(p->{
            p.setType(URI.create("http://example.com/problems/customer-not-found"));
            p.setTitle("Customer Not Found");
        },ex, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(NotEnoughBalanceException.class)
    public ProblemDetail handleInsufficientBalance(NotEnoughBalanceException ex) {
        return build(p->{
            p.setType(URI.create("http://example.com/problems/insufficient-balance"));
            p.setTitle("Insufficient balance");
        },ex, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NotEnoughSharesException.class)
    public ProblemDetail handleInsufficientShares(NotEnoughSharesException ex) {
        return build(p->{
            p.setType(URI.create("http://example.com/problems/insufficientShares"));
            p.setTitle("Insufficient shares");
        },ex, HttpStatus.BAD_REQUEST);
    }

    private ProblemDetail build(Consumer<ProblemDetail> consumer, Exception ex, HttpStatus status) {
        var problem = ProblemDetail.forStatusAndDetail(status, ex.getMessage());
        consumer.accept(problem);
        return problem;
    }
}

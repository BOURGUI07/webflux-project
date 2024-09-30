package com.example.aggregator_service.advice;

import com.example.aggregator_service.exceptions.CustomerNotFoundException;
import com.example.aggregator_service.exceptions.InvalidTradeRequestException;
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

    @ExceptionHandler(InvalidTradeRequestException.class)
    public ProblemDetail handleInsufficientBalance(InvalidTradeRequestException ex) {
        return build(p->{
            p.setType(URI.create("http://example.com/problems/invalid-trade-request"));
            p.setTitle("Invalid Trade Request");
        },ex, HttpStatus.BAD_REQUEST);
    }

    private ProblemDetail build(Consumer<ProblemDetail> consumer, Exception ex, HttpStatus status) {
        var problem = ProblemDetail.forStatusAndDetail(status, ex.getMessage());
        consumer.accept(problem);
        return problem;
    }
}

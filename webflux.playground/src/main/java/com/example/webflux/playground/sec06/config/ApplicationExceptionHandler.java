package com.example.webflux.playground.sec06.config;

import com.example.webflux.playground.sec06.exceptions.CustomerNotFoundException;
import com.example.webflux.playground.sec06.exceptions.InvalidInputException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.net.URI;

@Service
public class ApplicationExceptionHandler {

    public Mono<ServerResponse> handleCustomerNotFoundException(
            CustomerNotFoundException ex, ServerRequest request) {
        var status = HttpStatus.NOT_FOUND;
        var problem = ProblemDetail.forStatusAndDetail(status, ex.getMessage());
        problem.setType(URI.create("http://example.com/problems/customerNotFound"));
        problem.setTitle("Customer not found");
        problem.setInstance(URI.create(request.path()));
        return ServerResponse.status(status).bodyValue(problem);
    }

    public Mono<ServerResponse> handleInvalidInput(InvalidInputException ex, ServerRequest request) {
        var status = HttpStatus.BAD_REQUEST;
        var problem = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, ex.getMessage());
        problem.setType(URI.create("http://example.com/problems/invalidInput"));
        problem.setTitle("Invalid input");
        return ServerResponse.status(status).bodyValue(problem);
    }
}

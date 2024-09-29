package com.example.webflux.playground.sec06.config;

import com.example.webflux.playground.sec06.exceptions.CustomerNotFoundException;
import com.example.webflux.playground.sec06.exceptions.InvalidInputException;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

@Configuration
@RequiredArgsConstructor
public class RouterConfiguration {
    private final CustomerRequestHandler handler;
    private final ApplicationExceptionHandler exceptionHandler;
    @Bean
    public RouterFunction<ServerResponse> routerFunction() {
        return RouterFunctions.route()
                .GET("/api/customers",handler::allCustomers)
                .GET("/api/customers/paginated",handler::findAllPaginated)
                .GET("/api/customers/{id}",handler::findById)
                .POST("api/customers",handler::createCustomer)
                .PUT("api/customers/{id}",handler::updateCustomer)
                .DELETE("api/customers/{id}",handler::deleteCustomer)
                .onError(CustomerNotFoundException.class,exceptionHandler::handleCustomerNotFoundException)
                .onError(InvalidInputException.class,exceptionHandler::handleInvalidInput)
                .build();

    }


}

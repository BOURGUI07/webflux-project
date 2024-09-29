package com.example.webflux.playground.sec06.config;

import com.example.webflux.playground.sec05.dto.paginated.PaginatedCustomerResponse;
import com.example.webflux.playground.sec06.dto.request.CustomerRequest;
import com.example.webflux.playground.sec06.dto.response.CustomerResponse;
import com.example.webflux.playground.sec06.exceptions.ApplicationExceptions;
import com.example.webflux.playground.sec06.service.CustomerService;
import com.example.webflux.playground.sec06.validator.RequestValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class CustomerRequestHandler {
        private final CustomerService service;

    public Mono<ServerResponse> allCustomers(ServerRequest request) {
        //request.pathVariable()
        //request.headers()
        //request.queryParams()
        return service.findAll()
                .as(flux->ServerResponse.ok().body(flux, CustomerResponse.class));
    }

    public Mono<ServerResponse> findById(ServerRequest request) {
        var id = Integer.valueOf(request.pathVariable("id"));
        return service.findById(id)
                .switchIfEmpty(ApplicationExceptions.customerNoFoundException(id))
                .flatMap(ServerResponse.ok()::bodyValue);
    }

    public Mono<ServerResponse> createCustomer(ServerRequest request) {
        return request.bodyToMono(CustomerRequest.class)
                .transform(RequestValidator.isValid())
                .as(service::save)
                .flatMap(ServerResponse.ok()::bodyValue);

    }

    public Mono<ServerResponse> updateCustomer(ServerRequest request) {
        var id = Integer.valueOf(request.pathVariable("id"));
        return request.bodyToMono(CustomerRequest.class)
                .transform(RequestValidator.isValid())
                .as(validatedRequest -> service.update(id,validatedRequest))
                .switchIfEmpty(ApplicationExceptions.customerNoFoundException(id))
                .flatMap(ServerResponse.ok()::bodyValue);
    }

    public Mono<ServerResponse> deleteCustomer(ServerRequest request) {
        var id = Integer.valueOf(request.pathVariable("id"));
        return service.delete(id)
                .filter(x->x)
                .switchIfEmpty(ApplicationExceptions.customerNoFoundException(id))
                .then(ServerResponse.ok().build());

    }

    public Mono<ServerResponse> findAllPaginated(ServerRequest request) {
       int page = request.queryParam("page")
               .map(Integer::parseInt)
               .orElse(1);

        int size = request.queryParam("size")
                .map(Integer::parseInt)
                .orElse(3);

        return service.findAllPaginated(page,size)
                .as(mono->ServerResponse.ok().body(mono, PaginatedCustomerResponse.class));
    }


}

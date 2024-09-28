package com.example.webflux.playground.sec04.controller;

import com.example.webflux.playground.sec04.dto.paginated.PaginatedCustomerResponse;
import com.example.webflux.playground.sec04.dto.request.CustomerRequest;
import com.example.webflux.playground.sec04.dto.response.CustomerResponse;
import com.example.webflux.playground.sec04.exceptions.ApplicationExceptions;
import com.example.webflux.playground.sec04.service.CustomerService;
import com.example.webflux.playground.sec04.validator.RequestValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/customers")
public class CustomerController {
    private final CustomerService service;

    @GetMapping("/{id}")
    public Mono<CustomerResponse> findById(@PathVariable Integer id) {
        return service.findById(id)
                .switchIfEmpty(ApplicationExceptions.customerNoFoundException(id));
    }

    @GetMapping(produces= MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<CustomerResponse> findAll() {
        return service.findAll();
    }

    @PostMapping
    public Mono<CustomerResponse> save(@RequestBody Mono<CustomerRequest> request) {
        return request.transform(RequestValidator.isValid())
                .as(service::save);

    }

    @PutMapping("/{id}")
    public Mono<CustomerResponse> update(@PathVariable Integer id, @RequestBody Mono<CustomerRequest> request) {
        return request.transform(RequestValidator.isValid())
                .as(x-> service.update(id, x))
                .switchIfEmpty(ApplicationExceptions.customerNoFoundException(id));
    }

    @DeleteMapping("/{id}")
    public Mono<Void> delete(@PathVariable Integer id) {
        return service.delete(id)
                .filter(b->b)
                .switchIfEmpty(ApplicationExceptions.customerNoFoundException(id))
                .then();
    }

    @GetMapping(value = "/paginated")
    public Mono<PaginatedCustomerResponse> findByPage(
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "3") Integer size) {
        return service.findAllPaginated(page, size);
    }
}

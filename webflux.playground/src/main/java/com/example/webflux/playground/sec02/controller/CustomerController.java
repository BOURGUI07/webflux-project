package com.example.webflux.playground.sec02.controller;

import com.example.webflux.playground.sec02.dto.paginated.PaginatedCustomerResponse;
import com.example.webflux.playground.sec02.dto.request.CustomerRequest;
import com.example.webflux.playground.sec02.dto.response.CustomerResponse;
import com.example.webflux.playground.sec02.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/customers")
public class CustomerController {
    private final CustomerService service;

    @GetMapping("/{id}")
    public Mono<ResponseEntity<CustomerResponse>> findById(@PathVariable Integer id) {
        return service.findById(id)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @GetMapping(produces= MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<CustomerResponse> findAll() {
        return service.findAll();
    }

    @PostMapping
    public Mono<CustomerResponse> save(@RequestBody Mono<CustomerRequest> request) {
        return service.save(request);
    }

    @PutMapping("/{id}")
    public Mono<ResponseEntity<CustomerResponse>> update(@PathVariable Integer id, @RequestBody Mono<CustomerRequest> request) {
        return service.update(id, request)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public Mono<ResponseEntity<Void>> delete(@PathVariable Integer id) {
        return service.delete(id)
                .filter(b->b)
                .map(x->ResponseEntity.ok().<Void>build())
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @GetMapping(value = "/paginated")
    public Mono<PaginatedCustomerResponse> findByPage(
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "3") Integer size) {
        return service.findAllPaginated(page, size);
    }
}

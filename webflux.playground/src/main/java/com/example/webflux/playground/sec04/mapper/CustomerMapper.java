package com.example.webflux.playground.sec04.mapper;

import com.example.webflux.playground.sec04.dto.request.CustomerRequest;
import com.example.webflux.playground.sec04.dto.response.CustomerResponse;
import com.example.webflux.playground.sec04.entity.Customer;
import org.springframework.stereotype.Component;

@Component
public class CustomerMapper {
    public Customer toEntity(CustomerRequest request) {
        return new Customer().setName(request.name()).setEmail(request.email());
    }

    public CustomerResponse toResponse(Customer customer) {
        return new CustomerResponse(customer.getId(), customer.getName(), customer.getEmail());
    }
}

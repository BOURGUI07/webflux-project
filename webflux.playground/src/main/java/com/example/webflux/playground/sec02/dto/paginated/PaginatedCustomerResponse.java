package com.example.webflux.playground.sec02.dto.paginated;

import com.example.webflux.playground.sec02.dto.response.CustomerResponse;
import com.example.webflux.playground.sec02.entity.Customer;
import reactor.core.publisher.Flux;

import java.util.List;

public record PaginatedCustomerResponse(
        List<CustomerResponse> customers,
        long totalCount
) {
}

package com.example.webflux.playground.sec05.dto.paginated;

import com.example.webflux.playground.sec05.dto.response.CustomerResponse;

import java.util.List;

public record PaginatedCustomerResponse(
        List<CustomerResponse> customers,
        long totalCount
) {
}

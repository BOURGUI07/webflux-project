package com.example.webflux.playground.sec06.dto.paginated;

import com.example.webflux.playground.sec06.dto.response.CustomerResponse;

import java.util.List;

public record PaginatedCustomerResponse(
        List<CustomerResponse> customers,
        long totalCount
) {
}

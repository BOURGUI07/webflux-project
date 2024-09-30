package com.example.aggregator_service.dto;


import com.example.aggregator_service.domain.Ticker;

public record PortfolioItemRequest(
        Integer customerId,
        Ticker ticker,
        Integer quantity

) {
}

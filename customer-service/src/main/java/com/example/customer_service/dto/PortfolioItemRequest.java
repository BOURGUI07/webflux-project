package com.example.customer_service.dto;

import com.example.customer_service.entity.Ticker;

public record PortfolioItemRequest(
        Integer customerId,
        Ticker ticker,
        Integer quantity

) {
}

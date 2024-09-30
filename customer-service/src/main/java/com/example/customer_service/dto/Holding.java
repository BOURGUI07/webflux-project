package com.example.customer_service.dto;

import com.example.customer_service.entity.Ticker;

public record Holding(
        Ticker ticker,
        Integer quantity
) {
}

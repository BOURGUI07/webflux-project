package com.example.aggregator_service.dto;


import com.example.aggregator_service.domain.Ticker;

public record Holding(
        Ticker ticker,
        Integer quantity
) {
}

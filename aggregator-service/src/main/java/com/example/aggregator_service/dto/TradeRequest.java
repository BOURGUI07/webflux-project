package com.example.aggregator_service.dto;

import com.example.aggregator_service.domain.Ticker;
import com.example.aggregator_service.domain.TradeAction;

public record TradeRequest(
        Ticker ticker,
        TradeAction action,
        Integer quantity
) {
}

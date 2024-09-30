package com.example.aggregator_service.dto;


import com.example.aggregator_service.domain.Ticker;
import com.example.aggregator_service.domain.TradeAction;

public record StockTradeRequest(
        Ticker ticker,
        Integer price,
        Integer quantity,
        TradeAction action
) {
}

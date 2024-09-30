package com.example.customer_service.dto;

import com.example.customer_service.entity.Ticker;
import com.example.customer_service.entity.TradeAction;

public record StockTradeResponse(
        Integer customerId,
        Ticker ticker,
        Integer price,
        Integer quantity,
        TradeAction action,
        Integer totalPrice,
        Integer balance
) {
}

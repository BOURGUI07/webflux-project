package com.example.customer_service.dto;

import com.example.customer_service.entity.Ticker;
import com.example.customer_service.entity.TradeAction;

public record StockTradeRequest(
        Ticker ticker,
        Integer price,
        Integer quantity,
        TradeAction action
) {
    public Integer getTotalPrice(){
        return price * quantity;
    }
}

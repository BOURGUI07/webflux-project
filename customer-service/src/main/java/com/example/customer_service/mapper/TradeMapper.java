package com.example.customer_service.mapper;

import com.example.customer_service.dto.StockTradeRequest;
import com.example.customer_service.dto.StockTradeResponse;
import com.example.customer_service.entity.Customer;
import org.springframework.stereotype.Component;

@Component
public class TradeMapper {
    public StockTradeResponse toResponse(Customer customer, StockTradeRequest request) {
        var price = request.price();
        var quantity = request.quantity();
        return new StockTradeResponse(
                customer.getId(),
                request.ticker(),
                price,
                quantity,
                request.action(),
                request.getTotalPrice(),
                customer.getBalance()
        );
    }
}

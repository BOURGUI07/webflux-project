package com.example.customer_service.mapper;

import com.example.customer_service.dto.PortfolioItemRequest;
import com.example.customer_service.entity.PortfolioItem;
import org.springframework.stereotype.Component;

@Component
public class PortfolioMapper {
    public PortfolioItem toEntity(PortfolioItemRequest request){
        return new PortfolioItem().setQuantity(request.quantity())
                .setCustomerId(request.customerId())
                .setTicker(request.ticker());
    }
}

package com.example.aggregator_service.service;

import com.example.aggregator_service.client.CustomerServiceClient;
import com.example.aggregator_service.client.StockServiceClient;
import com.example.aggregator_service.dto.*;
import com.example.aggregator_service.validator.RequestValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class CustomerPortfolioService {
    private final CustomerServiceClient customerServiceClient;
    private final StockServiceClient stockServiceClient;

    public Mono<CustomerInformation> getCustomerInformation(Integer customerId) {
        return customerServiceClient.getCustomerInformation(customerId);
    }

    public Mono<StockTradeResponse> trade(Integer customerId, TradeRequest request){
        return stockServiceClient.getTickerPrice(request.ticker())
                .map(StockPriceResponse::price)
                .map(x-> new StockTradeRequest(request.ticker(),x,request.quantity(),request.action()))
                .flatMap(x-> this.customerServiceClient.trade(customerId,x));
    }


}

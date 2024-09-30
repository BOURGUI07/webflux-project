package com.example.customer_service.controller;

import com.example.customer_service.dto.CustomerInformation;
import com.example.customer_service.dto.StockTradeRequest;
import com.example.customer_service.dto.StockTradeResponse;
import com.example.customer_service.service.CustomerService;
import com.example.customer_service.service.TradeService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/customers")
@RequiredArgsConstructor
public class CustomerController {
    private final CustomerService customerService;
    private final TradeService tradeService;


    @GetMapping("/{id}")
    public Mono<CustomerInformation> getCustomer(@PathVariable Integer id) {
        return customerService.getCustomerInformation(id);
    }

    @PostMapping("/{customerId}/trade")
    public Mono<StockTradeResponse> trade(@PathVariable Integer customerId, @RequestBody Mono<StockTradeRequest> stockTradeRequest) {
        return stockTradeRequest.flatMap(stockTrade -> tradeService.trade(customerId, stockTrade));
    }

}

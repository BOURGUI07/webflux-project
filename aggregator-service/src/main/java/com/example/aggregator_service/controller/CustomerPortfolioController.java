package com.example.aggregator_service.controller;

import com.example.aggregator_service.dto.CustomerInformation;
import com.example.aggregator_service.dto.StockTradeResponse;
import com.example.aggregator_service.dto.TradeRequest;
import com.example.aggregator_service.service.CustomerPortfolioService;
import com.example.aggregator_service.validator.RequestValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/customers")
public class CustomerPortfolioController {
    private final CustomerPortfolioService service;

    @GetMapping("/{customerId}")
    public Mono<CustomerInformation> getCustomerInformation(@PathVariable Integer customerId) {
        return service.getCustomerInformation(customerId);
    }

    @PostMapping("/{customerId}/trade")
    public Mono<StockTradeResponse> trade(
            @PathVariable Integer customerId,
            @RequestBody Mono<TradeRequest> tradeRequest
    ){
        return tradeRequest
                .transform(RequestValidator.validate())
                .flatMap(x-> service.trade(customerId, x));
    }

}

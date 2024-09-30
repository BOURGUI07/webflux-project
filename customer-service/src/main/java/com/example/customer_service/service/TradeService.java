package com.example.customer_service.service;

import com.example.customer_service.dto.PortfolioItemRequest;
import com.example.customer_service.dto.StockTradeRequest;
import com.example.customer_service.dto.StockTradeResponse;
import com.example.customer_service.entity.Customer;
import com.example.customer_service.entity.PortfolioItem;
import com.example.customer_service.exceptions.ApplicationExceptions;
import com.example.customer_service.exceptions.CustomerNotFoundException;
import com.example.customer_service.mapper.PortfolioMapper;
import com.example.customer_service.mapper.TradeMapper;
import com.example.customer_service.repo.CustomerRepo;
import com.example.customer_service.repo.PortfolioRepo;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true,level = AccessLevel.PRIVATE)
public class TradeService {
    CustomerRepo customerRepo;
    PortfolioRepo portfolioRepo;
    PortfolioMapper portfolioMapper;
    TradeMapper tradeMapper;

    @Transactional
    public Mono<StockTradeResponse> trade(Integer customerId, StockTradeRequest request) {
        return switch (request.action()){
            case BUY -> buyStock(customerId,request);
            case SELL -> sellStock(customerId,request);
        };
    }

    private Mono<StockTradeResponse> sellStock(Integer customerId, StockTradeRequest request) {
        var ticker = request.ticker();
        var portfolioCreationRequest = new PortfolioItemRequest(customerId,ticker, request.quantity());

        var customerMono = customerRepo.findById(customerId)
                .switchIfEmpty(Mono.error(new CustomerNotFoundException(customerId)))
                .switchIfEmpty(ApplicationExceptions.notEnoughBalance(customerId));

        var portfolionMono= portfolioRepo.findByCustomerIdAndTicker(customerId,ticker)
                .filter(x->x.getQuantity()>=request.quantity())
                .switchIfEmpty(ApplicationExceptions.notEnoughShares(customerId));

        return customerMono.zipWhen(customer -> portfolionMono)
                .flatMap(x->{
                    return executeSell(x.getT1(),x.getT2(),request);
                });
    }

    private Mono<StockTradeResponse> executeSell(Customer customer, PortfolioItem portfolioItem, StockTradeRequest request) {
        customer.setBalance(customer.getBalance()+ request.getTotalPrice());
        portfolioItem.setQuantity(portfolioItem.getQuantity()- request.quantity());
        return saveAndBuildResponse(customer,portfolioItem,request);
    }

    private Mono<StockTradeResponse> buyStock(Integer customerId, StockTradeRequest request) {
        var ticker = request.ticker();
        var portfolioCreationRequest = new PortfolioItemRequest(customerId,ticker, request.quantity());

        var customerMono = customerRepo.findById(customerId)
                .switchIfEmpty(Mono.error(new CustomerNotFoundException(customerId)))
                .filter(c->c.getBalance()>= request.getTotalPrice())
                .switchIfEmpty(ApplicationExceptions.notEnoughBalance(customerId));

        var portfolionMono= portfolioRepo.findByCustomerIdAndTicker(customerId,ticker)
                .defaultIfEmpty(portfolioMapper.toEntity(portfolioCreationRequest));

        return customerMono.zipWhen(customer -> portfolionMono)
                .flatMap(x->{
                    return executeBuy(x.getT1(),x.getT2(),request);
                });
    }

    private Mono<StockTradeResponse> executeBuy(Customer customer, PortfolioItem portfolioItem, StockTradeRequest request) {
        customer.setBalance(customer.getBalance()-request.getTotalPrice());
        portfolioItem.setQuantity(request.quantity()+portfolioItem.getQuantity());
        var response = tradeMapper.toResponse(customer,request);
        return saveAndBuildResponse(customer,portfolioItem,request);
    }

    private Mono<StockTradeResponse> saveAndBuildResponse(Customer customer, PortfolioItem portfolioItem, StockTradeRequest request){
        return Mono.zip(customerRepo.save(customer),portfolioRepo.save(portfolioItem))
                .thenReturn(tradeMapper.toResponse(customer,request));
    }
}

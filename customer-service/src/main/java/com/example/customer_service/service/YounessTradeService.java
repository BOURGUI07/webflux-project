package com.example.customer_service.service;

import com.example.customer_service.dto.PortfolioItemRequest;
import com.example.customer_service.dto.StockTradeRequest;
import com.example.customer_service.dto.StockTradeResponse;
import com.example.customer_service.entity.Customer;
import com.example.customer_service.entity.PortfolioItem;
import com.example.customer_service.entity.TradeAction;
import com.example.customer_service.exceptions.ApplicationExceptions;
import com.example.customer_service.mapper.PortfolioMapper;
import com.example.customer_service.mapper.TradeMapper;
import com.example.customer_service.repo.CustomerRepo;
import com.example.customer_service.repo.PortfolioRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class YounessTradeService {
    private final CustomerRepo repo;
    private final PortfolioRepo portfolioRepo;
    private final PortfolioMapper mapper;
    private final TradeMapper tradeMapper;

    public Mono<StockTradeResponse> trade(Integer customerId, Mono<StockTradeRequest> request) {
        return repo.findById(customerId)
                .switchIfEmpty(ApplicationExceptions.customerNoFoundException(customerId))
                .zipWith(request)
                .map(x->{
                    var customer = x.getT1();
                    var tradeRequest = x.getT2();
                    if(tradeRequest.action().equals(TradeAction.BUY)){
                         this.buy(customer, tradeRequest);
                    }else {
                         this.sell(customer, tradeRequest);
                    }
                    return tradeMapper.toResponse(customer,tradeRequest);
                });
    }

    public void buy(Customer customer, StockTradeRequest request) {
        var portfolioRequest = new PortfolioItemRequest(customer.getId(), request.ticker(), request.quantity());
        var totalPrice= request.price()* request.quantity();
        Mono.just(customer.getBalance()>=totalPrice)
                    .filter(b->b)
                    .switchIfEmpty(ApplicationExceptions.notEnoughBalance(customer.getBalance()))
                    .then(repo.save(customer.setBalance(customer.getBalance()-totalPrice)))
                    .then(portfolioRepo.findByCustomerIdAndTicker(customer.getId(),request.ticker()))
                    .switchIfEmpty(portfolioRepo.save(mapper.toEntity(portfolioRequest)))
                    .doOnNext(portfolio -> portfolio.setQuantity(portfolio.getQuantity()+request.quantity()))
                    .flatMap(portfolioRepo::save);


    }

    public void sell(Customer customer, StockTradeRequest request) {
        portfolioRepo.findByCustomerIdAndTicker(customer.getId(),request.ticker())
                .filter(portfolio -> portfolio.getQuantity()>=request.quantity())
                .switchIfEmpty(ApplicationExceptions.notEnoughShares(customer.getId()))
                .doOnNext(portfolio -> portfolio.setQuantity(portfolio.getQuantity()-request.quantity()))
                .flatMap(portfolioRepo::save)
                .then(repo.save(customer.setBalance(customer.getBalance()+request.quantity()*request.price())));
    }
}



package com.example.aggregator_service.validator;

import com.example.aggregator_service.domain.Ticker;
import com.example.aggregator_service.domain.TradeAction;
import com.example.aggregator_service.dto.TradeRequest;
import com.example.aggregator_service.exceptions.ApplicationExceptions;
import reactor.core.publisher.Mono;

import java.util.function.Predicate;
import java.util.function.UnaryOperator;

public class RequestValidator {

    public static Predicate<TradeRequest> hasTicker(){
        return  x-> x.ticker()!=null;
    }

    public static Predicate<TradeRequest> hasAction(){
        return  x-> x.action()!=null;
    }

    public static Predicate<TradeRequest> hasValidQuantity(){
        return  x-> x.quantity()!=null && x.quantity()>0;
    }

    public static UnaryOperator<Mono<TradeRequest>> validate(){
        return mono -> mono
                .filter(hasTicker())
                .switchIfEmpty(ApplicationExceptions.tickerIsMissing())
                .filter(hasAction())
                .switchIfEmpty(ApplicationExceptions.tradeActionIsMissing())
                .filter(hasValidQuantity())
                .switchIfEmpty(ApplicationExceptions.invalidQuantity());

    }
}

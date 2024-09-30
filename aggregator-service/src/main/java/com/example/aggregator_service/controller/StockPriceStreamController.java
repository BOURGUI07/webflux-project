package com.example.aggregator_service.controller;

import com.example.aggregator_service.client.StockServiceClient;
import com.example.aggregator_service.dto.PriceUpdate;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/stock")
public class StockPriceStreamController {
    private final StockServiceClient client;

    @GetMapping(value="/price-stream", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<PriceUpdate> getPriceUpdateStream() {
        return client.priceUpdateStream();
    }
}

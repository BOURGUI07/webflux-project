package com.example.aggregator_service.client;

import com.example.aggregator_service.domain.Ticker;
import com.example.aggregator_service.dto.PriceUpdate;
import com.example.aggregator_service.dto.StockPriceResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.util.retry.Retry;

import java.time.Duration;
import java.util.Objects;

@Slf4j
@RequiredArgsConstructor
public class StockServiceClient {
    private final WebClient webClient;
    private Flux<PriceUpdate> flux;

    public Mono<StockPriceResponse> getTickerPrice(Ticker ticker) {
        return webClient.get()
                .uri("/stock/{ticker}", ticker)
                .retrieve()
                .bodyToMono(StockPriceResponse.class);
    }

    private Flux<PriceUpdate> getPriceUpdate() {
         return webClient.get()
                .uri("/stock/price-stream")
                 .accept(MediaType.APPLICATION_NDJSON)
                 .retrieve()
                 .bodyToFlux(PriceUpdate.class)
                 .retryWhen(retry())
                 .cache(1);
    }

    private Retry retry() {
        return Retry.fixedDelay(100, Duration.ofSeconds(1))
                .doBeforeRetry(x-> log.info("STOCK SERVICE PRICE STREAM CALL FAILED RETRYING : {}",x.failure().getMessage()));
    }

    public Flux<PriceUpdate> priceUpdateStream() {
        if(Objects.isNull(flux)){
            this.flux = getPriceUpdate();
        }
        return flux;
    }
    
    
}

package com.example.customer_service;

import com.example.customer_service.dto.CustomerInformation;
import com.example.customer_service.dto.StockTradeRequest;
import com.example.customer_service.entity.Ticker;
import com.example.customer_service.entity.TradeAction;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.WebTestClient;

@SpringBootTest
@AutoConfigureWebTestClient
class CustomerServiceApplicationTests {

	@Autowired
	private WebTestClient webClient;

	@Test
	void contextLoads() {
		webClient.get()
				.uri("/api/customers/1")
				.exchange()
				.expectStatus().isOk()
				.expectBody()
				.jsonPath("$.id").isEqualTo("1")
				.jsonPath("$.name").isEqualTo("Sam")
				.jsonPath("$.balance").isEqualTo(10000)
				.jsonPath("$.holdings").isEmpty();
	}

	@Test
	void buy(){
		var request = new StockTradeRequest(Ticker.AMAZON,45, 14, TradeAction.BUY);
		webClient.post()
				.uri("/api/customers/1/trade")
				.bodyValue(request)
				.exchange()
				.expectStatus().isOk()
				.expectBody()
				.jsonPath("$.customerId").isEqualTo("1")
				.jsonPath("$.ticker").isEqualTo(Ticker.AMAZON.name())
				.jsonPath("$.price").isEqualTo(45)
				.jsonPath("$.quantity").isEqualTo(14)
				.jsonPath("$.action").isEqualTo(TradeAction.BUY.name())
				.jsonPath("$.totalPrice").isEqualTo(630)
				.jsonPath("$.balance").isEqualTo(9370);
	}

	@Test
	void sell(){
		var request = new StockTradeRequest(Ticker.AMAZON,45, 14, TradeAction.SELL);
		webClient.post()
				.uri("/api/customers/1/trade")
				.bodyValue(request)
				.exchange()
				.expectStatus().isOk()
				.expectBody()
				.jsonPath("$.customerId").isEqualTo("1")
				.jsonPath("$.ticker").isEqualTo(Ticker.AMAZON.name())
				.jsonPath("$.price").isEqualTo(45)
				.jsonPath("$.quantity").isEqualTo(14)
				.jsonPath("$.action").isEqualTo(TradeAction.SELL.name())
				.jsonPath("$.totalPrice").isEqualTo(630)
				.jsonPath("$.balance").isEqualTo(10000);
	}

}

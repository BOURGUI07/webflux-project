package com.example.aggregator_service;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.mockserver.client.MockServerClient;
import org.mockserver.springtest.MockServerTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.WebTestClient;

@SpringBootTest(properties = {
        "customer.service.url= http://localhost:${mockServerPort}/api/customers",
        "stock.service.url= http://localhost:${mockServerPort}"
})
@AutoConfigureWebTestClient
@MockServerTest
public abstract class AbstractIntegrationTest{

    public MockServerClient mockServerClient;
    @Autowired
    public WebTestClient client;

}

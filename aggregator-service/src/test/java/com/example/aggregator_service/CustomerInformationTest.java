package com.example.aggregator_service;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.mockserver.model.HttpRequest;
import org.mockserver.model.HttpResponse;
import org.mockserver.model.MediaType;

import java.util.Objects;

public class CustomerInformationTest extends AbstractIntegrationTest{
    String responseBody = """
            {
                "name": "Mike"
            }
            """;
    @Test
    void customerInformation() {
        // mock customer service first
        mockServerClient
                .when(HttpRequest.request("/api/customers"))
                .respond(
                        HttpResponse.response(responseBody)
                                .withStatusCode(200)
                                .withContentType(MediaType.APPLICATION_JSON)
                );
        client
                .get()
                .uri("/api/customers/2")
                .exchange()
                .expectBody()
                .consumeWith(entity -> System.out.println(new String(Objects.requireNonNull(entity.getResponseBody()))));
    }
}

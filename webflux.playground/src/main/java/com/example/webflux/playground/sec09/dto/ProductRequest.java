package com.example.webflux.playground.sec09.dto;

public record ProductRequest(
        String description,
        Integer price
) {
}

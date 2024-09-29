package com.example.webflux.playground.sec09.dto;

public record ProductResponse(
        Integer id,
        String description,
        Integer price
) {
}

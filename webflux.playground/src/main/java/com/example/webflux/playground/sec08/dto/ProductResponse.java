package com.example.webflux.playground.sec08.dto;

public record ProductResponse(
        Integer id,
        String description,
        Integer price
) {
}

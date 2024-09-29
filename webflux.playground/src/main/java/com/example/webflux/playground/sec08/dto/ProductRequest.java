package com.example.webflux.playground.sec08.dto;

public record ProductRequest(
        String description,
        Integer price
) {
}

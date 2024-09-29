package com.example.webflux.playground.sec07.dto;

public record Product(
        Integer id,
        String description,
        Integer price
) {
}

package com.example.webflux.playground.sec10.dto;

public record Product(
        Integer id,
        String description,
        Integer price
) {
}

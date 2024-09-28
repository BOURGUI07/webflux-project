package com.example.webflux.playground.sec04.dto.response;

public record CustomerResponse(
        Integer id,
        String name,
        String email
) {
}

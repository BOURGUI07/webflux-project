package com.example.webflux.playground.sec05.dto.response;

public record CustomerResponse(
        Integer id,
        String name,
        String email
) {
}

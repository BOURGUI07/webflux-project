package com.example.webflux.playground.sec06.dto.response;

public record CustomerResponse(
        Integer id,
        String name,
        String email
) {
}

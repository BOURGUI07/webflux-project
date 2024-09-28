package com.example.webflux.playground.sec02.dto.response;

public record CustomerResponse(
        Integer id,
        String name,
        String email
) {
}

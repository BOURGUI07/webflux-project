package com.example.webflux.playground.sec04.dto.request;

public record CustomerRequest(
        String name,
        String email
) {
}

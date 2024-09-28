package com.example.webflux.playground.sec02.dto.request;

public record CustomerRequest(
        String name,
        String email
) {
}

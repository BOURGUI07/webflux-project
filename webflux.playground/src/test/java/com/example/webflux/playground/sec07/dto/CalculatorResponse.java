package com.example.webflux.playground.sec07.dto;

public record CalculatorResponse(
        Integer first,
        Integer second,
        String operation,
        Double result
) {
}

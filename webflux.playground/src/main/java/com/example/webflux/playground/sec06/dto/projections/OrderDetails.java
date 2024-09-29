package com.example.webflux.playground.sec06.dto.projections;

import java.time.Instant;

public record OrderDetails(
        Integer orderId,
        String customerName,
        String productName,
        Integer amount,
        Instant orderDate
) {
}

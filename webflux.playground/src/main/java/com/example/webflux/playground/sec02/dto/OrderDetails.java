package com.example.webflux.playground.sec02.dto;

import java.time.Instant;
import java.util.UUID;

public record OrderDetails(
        Integer orderId,
        String customerName,
        String productName,
        Integer amount,
        Instant orderDate
) {
}

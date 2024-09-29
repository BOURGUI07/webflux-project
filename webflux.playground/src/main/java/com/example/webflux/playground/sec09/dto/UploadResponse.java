package com.example.webflux.playground.sec09.dto;

import java.util.UUID;

public record UploadResponse(
        UUID confirmationId,
        Long count
) {
}

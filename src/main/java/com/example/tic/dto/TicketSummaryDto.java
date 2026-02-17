package com.example.tic.dto;



import java.time.Instant;

public record TicketSummaryDto(
        Long id,
        String title,
        String content,
        Instant createdAt
) {}

package com.example.tic.dto;

import java.time.Instant;
import java.util.List;

public record TicketResponse(
        Long id,
        String title,
        String content,
        Instant createdAt,
        List<PersonResponse> persons
) {}

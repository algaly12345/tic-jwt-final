package com.example.tic.dto;

import java.time.Instant;

public record CreatePersonRequest(
        String name,
        String nationalId,
        String mobileAccess,
        Instant exitTime,
        Instant returnTime
) {}

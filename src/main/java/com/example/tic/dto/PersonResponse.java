package com.example.tic.dto;

import com.example.tic.entity.PersonStatus;

import java.time.Instant;

public record PersonResponse(
        Long id,
        String name,
        String nationalId,
        String mobileAccess,
        String userId,
        String userName,
        PersonStatus status,
        Instant exitTime,
        Instant returnTime
) {}
package com.example.tic.exception;

import java.time.Instant;

public record PersonExitRequest(
        Instant exitTime
) {}

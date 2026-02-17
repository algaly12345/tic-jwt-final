package com.example.tic.exception;


import java.time.Instant;

public record PersonReturnRequest(
        Instant returnTime
) {}

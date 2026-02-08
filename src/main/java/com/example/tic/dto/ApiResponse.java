package com.example.tic.dto;

import java.time.LocalDateTime;

public record ApiResponse(String message, boolean success, LocalDateTime timestamp) {}

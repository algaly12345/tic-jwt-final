package com.example.tic.exception;

import com.example.tic.dto.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public ResponseEntity<ApiResponse> handleMaxSize(MaxUploadSizeExceededException ex) {
        return ResponseEntity.status(413)
                .body(new ApiResponse("File too large. Please upload a smaller file.", false, LocalDateTime.now()));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse> handleAny(Exception ex) {
        return ResponseEntity.status(500)
                .body(new ApiResponse("Server error: " + ex.getMessage(), false, LocalDateTime.now()));
    }
}

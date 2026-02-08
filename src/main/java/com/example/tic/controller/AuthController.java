package com.example.tic.controller;

import com.example.tic.dto.ApiResponse;
import com.example.tic.dto.AuthResponse;
import com.example.tic.dto.LoginRequest;
import com.example.tic.service.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping(value = "/register", consumes = "multipart/form-data")
    public ResponseEntity<ApiResponse> register(
            @RequestParam String firstName,
            @RequestParam String lastName,
            @RequestParam String email,
            @RequestParam String password,
            @RequestParam(required = false) String phoneNumber,
            @RequestParam(required = false) String nationalId,
            @RequestPart(required = false) MultipartFile profileImage,
            @RequestPart(required = false) MultipartFile signatureImage
    ) throws Exception {

        return ResponseEntity.ok(
                authService.registerWithImages(firstName, lastName, email, password, phoneNumber, nationalId, profileImage, signatureImage)
        );
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest req) {
        return ResponseEntity.ok(authService.login(req));
    }
}

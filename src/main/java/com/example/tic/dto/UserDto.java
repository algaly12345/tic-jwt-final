package com.example.tic.dto;

public record UserDto(
        String userId,
        String firstName,
        String lastName,
        String email,
        String phoneNumber,
        String nationalId,
        String role,
        Integer isActive,
        String profileImage,
        String signatureImage
) {}

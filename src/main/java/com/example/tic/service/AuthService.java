package com.example.tic.service;

import com.example.tic.dto.*;
import com.example.tic.entity.User;
import com.example.tic.entity.UserId;
import com.example.tic.repository.UserRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class AuthService {

    private final UserRepository repo;
    private final PasswordEncoder encoder;
    private final AuthenticationManager authManager;
    private final JwtService jwtService;
    private final FileStorageService fileStorageService;

    public AuthService(UserRepository repo,
                       PasswordEncoder encoder,
                       AuthenticationManager authManager,
                       JwtService jwtService,
                       FileStorageService fileStorageService) {
        this.repo = repo;
        this.encoder = encoder;
        this.authManager = authManager;
        this.jwtService = jwtService;
        this.fileStorageService = fileStorageService;
    }

    public ApiResponse registerWithImages(
            String firstName,
            String lastName,
            String email,
            String password,
            String phoneNumber,
            String nationalId,
            MultipartFile profileImage,
            MultipartFile signatureImage
    ) throws Exception {

        if (repo.existsByEmail(email)) {
            return new ApiResponse("Email already exists", false, LocalDateTime.now());
        }
        if (nationalId != null && !nationalId.isBlank() && repo.existsByNationalId(nationalId)) {
            return new ApiResponse("National ID already exists", false, LocalDateTime.now());
        }

        User u = new User();
        u.setId(new UserId(UUID.randomUUID().toString()));
        u.setFirstName(firstName);
        u.setLastName(lastName);
        u.setEmail(email);
        u.setPhoneNumber(phoneNumber);
        u.setNationalId(nationalId);

        u.setPassword(encoder.encode(password));
        u.setRole("USER");
        u.setIsActive(1);

        String profilePath = fileStorageService.saveFile(profileImage, "profile");
        String signaturePath = fileStorageService.saveFile(signatureImage, "signature");
        u.setProfileImage(profilePath);
        u.setSignatureImage(signaturePath);

        repo.save(u);

        return new ApiResponse("Registered successfully", true, LocalDateTime.now());
    }



public AuthResponse login(LoginRequest req) {

    authManager.authenticate(
            new UsernamePasswordAuthenticationToken(req.identifier(), req.password())
    );

User u = (req.identifier() != null && req.identifier().contains("@"))
        ? repo.findByEmail(req.identifier())
            .orElseThrow(() -> new UsernameNotFoundException("User not found"))
        : repo.findByNationalId(req.identifier())
            .orElseThrow(() -> new UsernameNotFoundException("User not found"));


    if (u.getIsActive() == null || u.getIsActive() != 1) {
        throw new IllegalStateException("User account is not active");
    }

    String token = jwtService.generateToken(u);

    UserDto dto = new UserDto(
            u.getId() != null ? u.getId().getUserId() : null,
            u.getFirstName(),
            u.getLastName(),
            u.getEmail(),
            u.getPhoneNumber(),
            u.getNationalId(),
            u.getRole(),
            u.getIsActive(),
            u.getProfileImage(),
            u.getSignatureImage()
    );

    return new AuthResponse(token, dto);
}

}

package com.example.tic.service;

import com.example.tic.entity.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.function.Function;

@Service
public class JwtService {

    @Value("${security.jwt.secret}")
    private String secret;

    @Value("${security.jwt.expiration-ms:86400000}")
    private long expirationMs;

    private SecretKey key() {
        return new SecretKeySpec(secret.getBytes(StandardCharsets.UTF_8), "HmacSHA256");
    }

    public String generateToken(User u) {
        Date now = new Date();
        Date exp = new Date(now.getTime() + expirationMs);

        return Jwts.builder()
                .subject(u.getEmail())
                .claim("userId", u.getId() != null ? u.getId().getUserId() : null)
                .claim("firstName", u.getFirstName())
                .claim("lastName", u.getLastName())
                .claim("email", u.getEmail())
                .claim("phoneNumber", u.getPhoneNumber())
                .claim("nationalId", u.getNationalId())
                .claim("role", u.getRole())
                .claim("isActive", u.getIsActive())
                .claim("profileImage", u.getProfileImage())
                .claim("signatureImage", u.getSignatureImage())
                .issuedAt(now)
                .expiration(exp)
                .signWith(key())
                .compact();
    }

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public <T> T extractClaim(String token, Function<Claims, T> resolver) {
        Claims claims = Jwts.parser()
                .verifyWith(key())
                .build()
                .parseSignedClaims(token)
                .getPayload();
        return resolver.apply(claims);
    }

    public boolean isTokenValid(String token, String expectedEmail) {
        String sub = extractUsername(token);
        return sub != null && sub.equals(expectedEmail) && !isExpired(token);
    }

    private boolean isExpired(String token) {
        Date exp = extractClaim(token, Claims::getExpiration);
        return exp.before(new Date());
    }
}

package com.susan.digitalbanking.digital_banking_api.security;

import com.susan.digitalbanking.digital_banking_api.entity.Role;
import com.susan.digitalbanking.digital_banking_api.entity.UserEntity;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;


@Service
public class JwtService {

    // Secret key used to sign the JWT
    private static final String SECRET_KEY = "my-super-secret-key-my-super-secret-key";

    // Token expiration times
    private static final long ACCESS_TOKEN_EXPIRATION = 1000 * 60 * 15; // 15 minutes
    private static final long REFRESH_TOKEN_EXPIRATION = 1000 * 60 * 60 * 24 * 7; // 7 days

    // Create a signing key from the secret
    public Key getSigningKey() {
        return Keys.hmacShaKeyFor(SECRET_KEY.getBytes());
    }

    // Generate ACCESS token (short-lived)
    public String generateAccessToken(UserEntity user) {

        return Jwts.builder()
                .setSubject(user.getUsername())
                .claim("roles",
                        user.getRoles()
                                .stream()
                                .map(role -> role.getName())
                                .toList()
                )
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + ACCESS_TOKEN_EXPIRATION))
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    // Generate REFRESH token (long-lived)
    public String generateRefreshToken(UserEntity user) {

        return Jwts.builder()
                .setSubject(user.getUsername())
                .claim("roles", user.getRoles()
                        .stream()
                        .map(Role::getName)
                        .toList())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + REFRESH_TOKEN_EXPIRATION))
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    // Extract username from token
    public String extractUsername(String token) {

        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    // Validate token
    public boolean isTokenValid(String token, String username) {

        String extractedUsername = extractUsername(token);

        return extractedUsername.equals(username) && !isTokenExpired(token);
    }

    // Check if token expired
    private boolean isTokenExpired(String token) {

        Date expiration = Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getExpiration();

        return expiration.before(new Date());
    }
}

package com.susan.digitalbanking.digital_banking_api.service;

import com.susan.digitalbanking.digital_banking_api.dto.AuthResponse;
import com.susan.digitalbanking.digital_banking_api.dto.LoginRequest;
import com.susan.digitalbanking.digital_banking_api.security.CustomUserDetailsService;
import com.susan.digitalbanking.digital_banking_api.security.JwtService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final CustomUserDetailsService customUserDetailsService;

    // Constructor injection for all dependencies
    public AuthService(AuthenticationManager authenticationManager,
                       JwtService jwtService,
                       CustomUserDetailsService customUserDetailsService) {
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
        this.customUserDetailsService = customUserDetailsService;
    }

    // Login method
    public AuthResponse login(LoginRequest request) {

        // Authenticate credentials (email + password)
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()
                )
        );

        // Load full user details (needed for JWT generation)
        String username = request.getUsername();

        // Generate JWT tokens (access + refresh) using username
        String accessToken = jwtService.generateAccessToken(username);
        String refreshToken = jwtService.generateRefreshToken(username);

        // 4 Return tokens in response DTO
        return new AuthResponse(accessToken, refreshToken);
    }
    
    public AuthResponse refreshToken(String refreshToken) {

        //Extract username from refresh token
        String username = jwtService.extractUsername(refreshToken);

        //Validate refresh token
        if (!jwtService.isTokenValid(refreshToken, username)) {
            throw new RuntimeException("Invalid refresh token");

        }

        //Generate new access token
        String newAccessToken = jwtService.generateAccessToken(username);

        //return new tokens
        return new AuthResponse(newAccessToken, refreshToken);
    }
}
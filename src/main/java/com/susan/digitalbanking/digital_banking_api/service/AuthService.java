package com.susan.digitalbanking.digital_banking_api.service;

import com.susan.digitalbanking.digital_banking_api.dto.AuthResponse;
import com.susan.digitalbanking.digital_banking_api.dto.LoginRequest;
import com.susan.digitalbanking.digital_banking_api.entity.UserEntity;
import com.susan.digitalbanking.digital_banking_api.repository.UserRepository;
import com.susan.digitalbanking.digital_banking_api.security.JwtService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final UserRepository userRepository;

    public AuthService(AuthenticationManager authenticationManager,
                       JwtService jwtService,
                       UserRepository userRepository) {
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
        this.userRepository = userRepository;
    }

    // LOGIN
    public AuthResponse login(LoginRequest request) {

        // 1 Authenticate credentials
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()
                )
        );

        // 2 Load user from database
        UserEntity user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        //Generate
        String accessToken = jwtService.generateAccessToken(user);
        String refreshToken = jwtService.generateRefreshToken(user);


        return new AuthResponse(accessToken, refreshToken);
    }


       // REFRESH TOKEN
       public AuthResponse refreshToken(String refreshToken) {

        // Extract username from token
        String username = jwtService.extractUsername(refreshToken);

        // Load user
        UserEntity user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        // 3 Validate refresh token
        if (!jwtService.isTokenValid(refreshToken, username)) {
            throw new RuntimeException("Invalid refresh token");
        }

        // 4 Generate new access token WITH roles
        String newAccessToken = jwtService.generateAccessToken(user);

        // 5 Return tokens
        return new AuthResponse(newAccessToken, refreshToken);
    }
}
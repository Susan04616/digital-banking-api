package com.susan.digitalbanking.digital_banking_api.controller;

import com.susan.digitalbanking.digital_banking_api.dto.AuthResponse;
import com.susan.digitalbanking.digital_banking_api.dto.LoginRequest;
import com.susan.digitalbanking.digital_banking_api.dto.RefreshRequest;
import com.susan.digitalbanking.digital_banking_api.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    // Login endpoint
    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest request) {
        AuthResponse response = authService.login(request);
        return ResponseEntity.ok(response);
    }

    //Refresh token
    @PostMapping("/refresh")
    public AuthResponse refresh(@RequestBody RefreshRequest request){
        return authService.refreshToken(request.getRefreshToken());
    }
}
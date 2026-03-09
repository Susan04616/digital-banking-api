package com.susan.digitalbanking.digital_banking_api.security;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder(){
        return  new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http)throws Exception{
        http
                // Disable CSRF since we are building a REST API
                .csrf(csrf -> csrf.disable())

                // Configure which endpoints are allowed without authentication
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/auth/**").permitAll() // login & refresh token endpoints
                        .anyRequest().authenticated() // everything else requires authentication
                );

        return http.build();

    }
}

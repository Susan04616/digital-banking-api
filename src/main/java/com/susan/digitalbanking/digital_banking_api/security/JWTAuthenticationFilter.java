package com.susan.digitalbanking.digital_banking_api.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class JWTAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;

    public JWTAuthenticationFilter(JwtService jwtService) {
        this.jwtService = jwtService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {

        String header = request.getHeader("Authorization");

        if (header == null || !header.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        String token = header.substring(7);

        try {

            Claims claims = Jwts.parserBuilder()
                    .setSigningKey(jwtService.getSigningKey())
                    .build()
                    .parseClaimsJws(token)
                    .getBody();

            String username = claims.getSubject();

            List<String> roles = claims.get("roles", List.class);

            List<SimpleGrantedAuthority> authorities =
                    roles.stream()
                            .map(SimpleGrantedAuthority::new)
                            .collect(Collectors.toList());

            UsernamePasswordAuthenticationToken authentication =
                    new UsernamePasswordAuthenticationToken(
                            username,
                            null,
                            authorities
                    );

            SecurityContextHolder.getContext().setAuthentication(authentication);

        } catch (Exception e) {
            System.out.println("JWT error: " + e.getMessage());
        }

        filterChain.doFilter(request, response);
    }
}
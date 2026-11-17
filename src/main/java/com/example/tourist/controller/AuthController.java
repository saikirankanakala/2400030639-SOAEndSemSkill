package com.example.tourist.controller;

import com.example.tourist.dto.LoginRequest;
import com.example.tourist.dto.TokenResponse;
import com.example.tourist.exception.UnauthorizedException;
import com.example.tourist.security.JwtUtil;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final JwtUtil jwtUtil;
    private final String username;
    private final String password;

    public AuthController(JwtUtil jwtUtil,
                          @Value("${app.auth.username}") String username,
                          @Value("${app.auth.password}") String password) {
        this.jwtUtil = jwtUtil;
        this.username = username;
        this.password = password;
    }

    @PostMapping("/login")
    public TokenResponse login(@Valid @RequestBody LoginRequest request) {
        if (!username.equals(request.getUsername()) || !password.equals(request.getPassword())) {
            throw new UnauthorizedException("Invalid username or password");
        }
        return new TokenResponse(jwtUtil.generateToken(request.getUsername()));
    }
}

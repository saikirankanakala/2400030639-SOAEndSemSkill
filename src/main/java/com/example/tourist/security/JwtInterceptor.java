package com.example.tourist.security;

import com.example.tourist.exception.UnauthorizedException;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

/**
 * STEP 1 - Validate the token request.
 * Every call to /api/tourists/** must carry:  Authorization: Bearer <jwt>
 */
@Component
public class JwtInterceptor implements HandlerInterceptor {

    private final JwtUtil jwtUtil;

    public JwtInterceptor(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        if ("OPTIONS".equalsIgnoreCase(request.getMethod())) {
            return true; // CORS pre-flight
        }

        String header = request.getHeader("Authorization");
        if (header == null || !header.startsWith("Bearer ")) {
            throw new UnauthorizedException("Missing or malformed Authorization header. Use: Bearer <token>");
        }

        String token = header.substring(7).trim();
        try {
            String username = jwtUtil.validateAndGetUsername(token);
            request.setAttribute("authenticatedUser", username);
        } catch (JwtException | IllegalArgumentException ex) {
            throw new UnauthorizedException("Invalid or expired token");
        }
        return true;
    }
}

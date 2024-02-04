package com.example.tasklist.service;

/**
 * Service for auth
 */
public interface AuthService {
    JwtResponse login(JwtRequest loginRequest);

    JwtResponse refresh(String refreshToken);
}

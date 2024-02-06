package com.example.tasklist.web.dto.auth;

import lombok.Data;
/**
 * Data Transfer Object (DTO) representing a response containing JWT information after successful authentication.
 */
@Data
public class JwtResponse {
    /**
     * User's unique identifier.
     */
    private Long id;
    /**
     * User's username.
     */
    private String userName;
    /**
     * Access token for authenticated requests.
     */
    private String accessToken;
    /**
     * Refresh token for obtaining a new access token.
     */
    private String refreshToken;

}

package com.example.tasklist.web.dto.auth;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
/**
 * Data Transfer Object (DTO) representing a request for JWT (JSON Web Token) authentication.
 */

@Data
public class JwtRequest {
    /**
     * Username entered during authentication.
     */
    @NotNull(message = "Username must be not null.") // он требует валидации, так как мы туда отправляем данные
    private String userName;
    /**
     * Password entered during authentication.
     */
    @NotNull(message = "Password must be not null.")
    private String password;
}

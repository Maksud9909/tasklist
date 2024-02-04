package com.example.tasklist.domain.exception;

/**
 * Пользователь должен получать, только свои задания, а если будут инфо о других,
 * то он выдаст ошибку
 */
public class AccessDeniedException extends RuntimeException {
    public AccessDeniedException() {
        super();
    }
}

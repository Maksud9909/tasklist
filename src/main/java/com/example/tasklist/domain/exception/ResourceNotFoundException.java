package com.example.tasklist.domain.exception;


/**
 * Здесь он будет выбрасывать исключение, когда не будет находить инфу с бд
 */
public class ResourceNotFoundException extends RuntimeException{
    public ResourceNotFoundException(String message) { // если ресурс не найдется, то можно написать собственную ошибку
        super(message);
    }
}

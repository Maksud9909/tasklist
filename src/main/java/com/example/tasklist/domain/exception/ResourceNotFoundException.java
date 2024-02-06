package com.example.tasklist.domain.exception;


/**
 * Обычно используется, когда код или сервис пытается получить доступ к какому-то ресурсу
 * (например, файлу, записи в базе данных, веб-странице), но этот ресурс не существует.
 */
public class ResourceNotFoundException extends RuntimeException{
    public ResourceNotFoundException(String message) { // если ресурс не найдется, то можно написать собственную ошибку
        super(message);
    }
}

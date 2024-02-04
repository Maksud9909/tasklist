package com.example.tasklist.domain.exception;

/**
 * When we will have problems with taking objects from dataBase, then it will work
 */
public class ResourceMappingException extends RuntimeException{
    public ResourceMappingException(String message) {
        super(message);
    }
}

package com.habitstack.exception;

import org.springframework.http.HttpStatus;

public class ResourceAlreadyExistsException extends BaseException {
    
    public ResourceAlreadyExistsException(String message) {
        super(message, HttpStatus.CONFLICT, "RESOURCE_ALREADY_EXISTS");
    }
    
    public ResourceAlreadyExistsException(String resourceType, String resourceName) {
        super(String.format("%s with name '%s' already exists", resourceType, resourceName), 
              HttpStatus.CONFLICT, "RESOURCE_ALREADY_EXISTS");
    }
    
    public ResourceAlreadyExistsException(String message, Object details) {
        super(message, HttpStatus.CONFLICT, "RESOURCE_ALREADY_EXISTS", details);
    }
}
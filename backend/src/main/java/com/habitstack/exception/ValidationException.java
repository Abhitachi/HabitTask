package com.habitstack.exception;

import org.springframework.http.HttpStatus;

public class ValidationException extends BaseException {
    
    public ValidationException(String message) {
        super(message, HttpStatus.BAD_REQUEST, "VALIDATION_ERROR");
    }
    
    public ValidationException(String field, String message) {
        super(String.format("Validation error for field '%s': %s", field, message), 
              HttpStatus.BAD_REQUEST, "VALIDATION_ERROR");
    }
    
    public ValidationException(String message, Object details) {
        super(message, HttpStatus.BAD_REQUEST, "VALIDATION_ERROR", details);
    }
}
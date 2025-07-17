package com.habitstack.exception;

import org.springframework.http.HttpStatus;

public class DatabaseException extends BaseException {
    
    public DatabaseException(String message) {
        super(message, HttpStatus.INTERNAL_SERVER_ERROR, "DATABASE_ERROR");
    }
    
    public DatabaseException(String message, Throwable cause) {
        super(message, cause, HttpStatus.INTERNAL_SERVER_ERROR, "DATABASE_ERROR");
    }
    
    public DatabaseException(String operation, String entity, Throwable cause) {
        super(String.format("Database error during %s operation for %s", operation, entity), 
              cause, HttpStatus.INTERNAL_SERVER_ERROR, "DATABASE_ERROR");
    }
}
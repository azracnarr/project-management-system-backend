package com.example.demo.exception;

// src/main/java/com/example/demo/exception/UnauthorizedException.java


public class UnauthorizedException extends RuntimeException {

    // Default constructor
    public UnauthorizedException() {
        super("Bu işlemi gerçekleştirmeye yetkiniz yok.");
    }

    // Constructor with a custom message
    public UnauthorizedException(String message) {
        super(message);
    }
}
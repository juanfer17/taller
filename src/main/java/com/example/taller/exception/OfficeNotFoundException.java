package com.example.taller.exception;

public class OfficeNotFoundException extends RuntimeException {
    public OfficeNotFoundException(String message) {
        super(message);
    }
}
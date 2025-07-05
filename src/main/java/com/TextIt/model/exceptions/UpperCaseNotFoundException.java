package com.TextIt.model.exceptions;

public class UpperCaseNotFoundException extends RuntimeException {
    public UpperCaseNotFoundException(String message) {
        super(message);
    }
}

package com.TextIt.model.exceptions;

public class LowerCaseNotFoundException extends RuntimeException {
    public LowerCaseNotFoundException(String message) {
        super(message);
    }
}

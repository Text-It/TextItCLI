package com.TextIt.model.exceptions;

public class SpecialCharacterNotFoundException extends RuntimeException {
    public SpecialCharacterNotFoundException(String message) {
        super(message);
    }
}

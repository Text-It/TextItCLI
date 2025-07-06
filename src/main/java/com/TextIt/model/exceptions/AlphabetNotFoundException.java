package com.TextIt.model.exceptions;

public class AlphabetNotFoundException extends RuntimeException {
    public AlphabetNotFoundException(String message) {
        super(message);
    }
}

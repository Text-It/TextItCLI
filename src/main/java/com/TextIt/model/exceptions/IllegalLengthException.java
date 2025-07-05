package com.TextIt.model.exceptions;

public class IllegalLengthException extends RuntimeException {
    public IllegalLengthException(String message) {
        super(message);
    }
}

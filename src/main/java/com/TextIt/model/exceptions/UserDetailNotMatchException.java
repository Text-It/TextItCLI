package com.TextIt.model.exceptions;

public class UserDetailNotMatchException extends RuntimeException {
    public UserDetailNotMatchException(String message) {
        super(message);
    }
}

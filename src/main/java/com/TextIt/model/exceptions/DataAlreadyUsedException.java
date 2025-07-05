package com.TextIt.model.exceptions;

public class DataAlreadyUsedException extends RuntimeException {
  public DataAlreadyUsedException(String message) {
    super(message);
  }
}

package com.nazobenkyo.petvaccine.application.api.exception.model;

public class UserException extends RuntimeException {
    public UserException() {
        super();
    }

    public UserException(String message) {
        super(message);
    }
}

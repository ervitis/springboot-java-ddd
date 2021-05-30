package com.nazobenkyo.petvaccine.application.api.exception.model;

public class UserAlreadyExists extends UserException {
    public UserAlreadyExists() {
        super();
    }

    public UserAlreadyExists(String message) {
        super(message);
    }
}

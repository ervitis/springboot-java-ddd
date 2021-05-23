package com.nazobenkyo.petvaccine.application.api.exception.model;

public class ForbiddenException extends UserException {
    public ForbiddenException() {
        super("Forbidden action");
    }
}

package com.nazobenkyo.petvaccine.application.api.security.jwt.exception;

public class JWTProviderException extends RuntimeException {
    public JWTProviderException() {
        super();
    }

    public JWTProviderException(String message) {
        super(message);
    }
}

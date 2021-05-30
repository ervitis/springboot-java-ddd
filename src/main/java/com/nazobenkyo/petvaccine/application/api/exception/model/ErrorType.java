package com.nazobenkyo.petvaccine.application.api.exception.model;

import org.springframework.http.HttpStatus;

public enum ErrorType {
    NotFound(HttpStatus.NOT_FOUND.value(), "value for data not found"),
    InternalServer(HttpStatus.INTERNAL_SERVER_ERROR.value(), "something internal happened"),
    NotValid(HttpStatus.BAD_REQUEST.value(), "input value data not valid"),
    UnsupportedMedia(HttpStatus.UNSUPPORTED_MEDIA_TYPE.value(), "unsupported media value"),
    Forbidden(HttpStatus.FORBIDDEN.value(), "forbidden for this user");

    private int code;
    private String message;

    public String getMessage() {
        return this.message;
    }

    public int getCode() {
        return this.code;
    }

    ErrorType(final int code, final String message) {
        this.message = message;
        this.code = code;
    }
}

package com.nazobenkyo.petvaccine.application.api.exception;

import com.nazobenkyo.petvaccine.application.api.exception.model.ErrorResponse;
import com.nazobenkyo.petvaccine.application.api.exception.model.ErrorType;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import javax.servlet.http.HttpServletResponse;

public class JSONExceptionFactory extends ExceptionFactory {

    private final HttpServletResponse response;
    private final HttpStatus httpStatus;

    public JSONExceptionFactory(HttpServletResponse response, HttpStatus httpStatus) {
        this.response = response;
        this.httpStatus = httpStatus;
    }

    @Override
    public ErrorResponse createError(ErrorType error) {
        this.response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        this.response.setStatus(this.httpStatus.value());
        return super.createError(error);
    }
}

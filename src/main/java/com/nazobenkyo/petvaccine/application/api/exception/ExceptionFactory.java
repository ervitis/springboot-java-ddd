package com.nazobenkyo.petvaccine.application.api.exception;

import com.nazobenkyo.petvaccine.application.api.exception.model.ErrorResponse;
import com.nazobenkyo.petvaccine.application.api.exception.model.ErrorType;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.time.Instant;

public class ExceptionFactory {
    private static final String DATE_FORMATTER = "yyyy-MM-dd'T'HH:mm:ssZ";

    private ErrorResponse createErrorResponse(ErrorType errorType) {
        return ErrorResponse
                .builder()
                .message(errorType.getMessage())
                .code(errorType.getCode())
                .date(new SimpleDateFormat(DATE_FORMATTER).format(Date.from(Instant.now())))
                .build();
    }

    public ErrorResponse createError(ErrorType error) {
        return this.createErrorResponse(error);
    }

}

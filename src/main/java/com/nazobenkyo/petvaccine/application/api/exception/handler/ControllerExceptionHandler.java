package com.nazobenkyo.petvaccine.application.api.exception.handler;

import com.nazobenkyo.petvaccine.application.api.exception.ExceptionFactory;
import com.nazobenkyo.petvaccine.application.api.exception.model.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.validation.ConstraintViolationException;

@Slf4j
@RestControllerAdvice
public class ControllerExceptionHandler extends ResponseEntityExceptionHandler {

    private final ExceptionFactory factory;
    public ControllerExceptionHandler() {
        this.factory = new ExceptionFactory();
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(code = HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponse genericHandlerException(RuntimeException ex, WebRequest request) {
        log.error("Exception unhandled", ex);
        return this.factory.createError(ErrorType.InternalServer);
    }

    @ExceptionHandler({ConstraintViolationException.class, InvalidDataException.class})
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    public ErrorResponse badRequestExceptionHandler(RuntimeException ex, WebRequest request) {
        return this.factory.createError(ErrorType.NotValid);
    }

    @ExceptionHandler({AccessDeniedException.class, AuthenticationException.class, ForbiddenException.class})
    @ResponseStatus(code = HttpStatus.FORBIDDEN)
    public ErrorResponse accessDeniedExceptionHandler(RuntimeException ex, WebRequest request) {
        return this.factory.createError(ErrorType.Forbidden);
    }

    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(code = HttpStatus.NOT_FOUND)
    public ErrorResponse notFoundExceptionHandler(RuntimeException ex, WebRequest request) {
        return this.factory.createError(ErrorType.NotFound);
    }
}

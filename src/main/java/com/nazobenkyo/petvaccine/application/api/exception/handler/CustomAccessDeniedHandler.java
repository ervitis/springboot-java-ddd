package com.nazobenkyo.petvaccine.application.api.exception.handler;

import com.nazobenkyo.petvaccine.application.api.exception.JSONExceptionFactory;
import com.nazobenkyo.petvaccine.application.api.exception.model.ErrorType;
import com.nazobenkyo.petvaccine.domain.mapper.ToJSON;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class CustomAccessDeniedHandler implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AccessDeniedException e) throws IOException, ServletException {
        JSONExceptionFactory jsonExceptionFactory = new JSONExceptionFactory(httpServletResponse, HttpStatus.FORBIDDEN);
        PrintWriter out = httpServletResponse.getWriter();
        out.print(ToJSON.convertJsonString(jsonExceptionFactory.createError(ErrorType.Forbidden)));
        out.flush();
        out.close();
    }
}

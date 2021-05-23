package com.nazobenkyo.petvaccine.application.api.exception.handler;

import com.nazobenkyo.petvaccine.application.api.exception.JSONExceptionFactory;
import com.nazobenkyo.petvaccine.application.api.exception.model.ErrorType;
import com.nazobenkyo.petvaccine.mapper.ToJSON;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class CustomAuthenticationEntrypoint implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException, ServletException {
        if (e == null) {
            return;
        }
        JSONExceptionFactory jsonExceptionFactory = new JSONExceptionFactory(httpServletResponse, HttpStatus.FORBIDDEN);
        PrintWriter out = httpServletResponse.getWriter();
        out.print(ToJSON.convertJsonString(jsonExceptionFactory.createError(ErrorType.Forbidden)));
        out.flush();
        out.close();
    }
}

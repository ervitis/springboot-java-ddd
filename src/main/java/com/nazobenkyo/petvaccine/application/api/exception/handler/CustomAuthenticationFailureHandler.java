package com.nazobenkyo.petvaccine.application.api.exception.handler;

import com.nazobenkyo.petvaccine.application.api.exception.JSONExceptionFactory;
import com.nazobenkyo.petvaccine.application.api.exception.model.ErrorType;
import com.nazobenkyo.petvaccine.domain.mapper.ToJSON;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@Component
public class CustomAuthenticationFailureHandler implements AuthenticationFailureHandler {

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        JSONExceptionFactory jsonExceptionFactory = new JSONExceptionFactory(response, HttpStatus.UNAUTHORIZED);
        PrintWriter out = response.getWriter();
        out.print(ToJSON.convertJsonString(jsonExceptionFactory.createError(ErrorType.Unauthorized)));
        out.flush();
        out.close();
    }
}

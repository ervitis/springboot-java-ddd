package com.nazobenkyo.petvaccine.application.api.security.permission.domain;

import com.nazobenkyo.petvaccine.application.api.security.jwt.JWTConstants;
import com.nazobenkyo.petvaccine.application.api.security.jwt.jwtprovider.JWTModel;
import com.nazobenkyo.petvaccine.application.api.security.jwt.jwtprovider.JWTPayloadProvider;
import com.nazobenkyo.petvaccine.application.api.security.jwt.jwtprovider.JWTProvider;
import org.springframework.security.access.expression.SecurityExpressionRoot;
import org.springframework.security.access.expression.method.MethodSecurityExpressionOperations;
import org.springframework.security.core.Authentication;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

public class JWTRoleSecurityExpressionRoot extends SecurityExpressionRoot implements MethodSecurityExpressionOperations {
    public JWTRoleSecurityExpressionRoot(Authentication authentication) {
        super(authentication);
    }
    private Object filterObject;
    private Object returnObject;

    public boolean userHasRole(String role) {
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
        JWTProvider jwtProvider = new JWTProvider();
        JWTModel jwt = jwtProvider.decode(requestAttributes.getRequest().getHeader(JWTConstants.HEADER_AUTHENTICATION_KEY));
        JWTPayloadProvider payloadProvider = new JWTPayloadProvider(jwt);
        return payloadProvider.hasRole(role);
    }

    @Override
    public void setFilterObject(Object filterObject) {
        this.filterObject = filterObject;
    }

    @Override
    public Object getFilterObject() {
        return this.filterObject;
    }

    @Override
    public void setReturnObject(Object returnObject) {
        this.returnObject = returnObject;
    }

    @Override
    public Object getReturnObject() {
        return this.returnObject;
    }

    @Override
    public Object getThis() {
        return this;
    }

}

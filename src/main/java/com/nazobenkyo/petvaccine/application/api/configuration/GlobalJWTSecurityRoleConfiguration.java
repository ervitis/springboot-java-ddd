package com.nazobenkyo.petvaccine.application.api.configuration;

import com.nazobenkyo.petvaccine.application.api.security.permission.JWTRoleSecurityExpressionHandler;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.expression.method.MethodSecurityExpressionHandler;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.method.configuration.GlobalMethodSecurityConfiguration;

@EnableGlobalMethodSecurity(prePostEnabled = true)
@Configuration
public class GlobalJWTSecurityRoleConfiguration extends GlobalMethodSecurityConfiguration {
    @Override
    protected MethodSecurityExpressionHandler createExpressionHandler() {
        return new JWTRoleSecurityExpressionHandler();
    }
}

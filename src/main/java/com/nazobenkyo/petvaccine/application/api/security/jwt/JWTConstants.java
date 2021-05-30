package com.nazobenkyo.petvaccine.application.api.security.jwt;

import java.util.concurrent.TimeUnit;

public class JWTConstants {
    public static final String HEADER_AUTHENTICATION_KEY = "Authorization";
    public static final String HEADER_AUTHENTICATION_BEARER = "Bearer";
    public static final String JWT_ISSUER = "clinicpet";
    public static final String SCOPE_FIELD = "scope";
    public static final String ROLES_FIELD = "roles";
    public static final long TOKEN_EXPIRATION_TIME = TimeUnit.MINUTES.toMillis(30);
}

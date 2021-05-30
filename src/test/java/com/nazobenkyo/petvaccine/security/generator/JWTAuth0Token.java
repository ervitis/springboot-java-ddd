package com.nazobenkyo.petvaccine.security.generator;

import com.auth0.jwt.JWT;
import com.nazobenkyo.petvaccine.application.api.security.jwt.JWTConstants;
import com.nazobenkyo.petvaccine.application.api.security.jwt.jwtprovider.JWTModel;
import com.nazobenkyo.petvaccine.application.api.security.jwt.jwtprovider.auth0.JWTAuth0Algorithm;

import java.util.Date;
import java.util.List;

import static com.nazobenkyo.petvaccine.application.api.security.jwt.JWTConstants.*;

public class JWTAuth0Token {

    public JWTModel createToken(String emailSubject, List<String> scopes, List<String> roles) {
        String value = JWT.create()
                .withIssuedAt(new Date())
                .withIssuer(JWTConstants.JWT_ISSUER)
                .withSubject(emailSubject)
                .withArrayClaim(SCOPE_FIELD, scopes.toArray(String[]::new))
                .withArrayClaim(ROLES_FIELD, roles.toArray(String[]::new))
                .withExpiresAt(new Date(System.currentTimeMillis() + TOKEN_EXPIRATION_TIME * 20))
                .sign(JWTAuth0Algorithm.getAlgorithm());
        return new JWTModel(value);
    }
}

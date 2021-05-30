package com.nazobenkyo.petvaccine.application.api.security.jwt.jwtprovider.auth0;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.nazobenkyo.petvaccine.application.api.security.jwt.JWTConstants;
import com.nazobenkyo.petvaccine.application.api.security.jwt.exception.JWTDecodingException;
import com.nazobenkyo.petvaccine.application.api.security.jwt.exception.JWTSubjectException;
import com.nazobenkyo.petvaccine.application.api.security.jwt.jwtprovider.JWTModel;
import com.nazobenkyo.petvaccine.model.Role;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Date;

import static com.nazobenkyo.petvaccine.application.api.security.jwt.JWTConstants.*;

public class JWTAuth0Provider {


    public JWTAuth0Provider() {
    }

    public JWTModel create(Authentication authentication, com.nazobenkyo.petvaccine.model.User user) throws JWTCreationException {
        String value = JWT.create()
                .withIssuedAt(new Date())
                .withIssuer(JWTConstants.JWT_ISSUER)
                .withSubject(((User) authentication.getPrincipal()).getUsername())
                .withArrayClaim(SCOPE_FIELD, authentication.getAuthorities().stream().map(GrantedAuthority::getAuthority).toArray(String[]::new))
                .withArrayClaim(ROLES_FIELD, user.getRoles().stream().map(Role::getName).toArray(String[]::new))
                .withExpiresAt(new Date(System.currentTimeMillis() + TOKEN_EXPIRATION_TIME))
                .sign(JWTAuth0Algorithm.getAlgorithm());
        return new JWTModel(value);
    }

    public JWTModel getSubject(String token) throws JWTDecodingException, JWTSubjectException {
        JWTVerifier verifier = JWT.require(JWTAuth0Algorithm.getAlgorithm())
                .withIssuer(JWTConstants.JWT_ISSUER)
                .build();
        String value;
        try {
            value = verifier.verify(this.getToken(token)).getSubject();
        } catch (JWTVerificationException exception) {
            throw new JWTDecodingException();
        }
        if (value == null || value.isEmpty()) {
            throw new JWTSubjectException("No subject from token");
        }
        JWTModel jwtModel = new JWTModel();
        jwtModel.setSubject(value);
        return jwtModel;
    }

    private String getToken(String token) {
        if (token.contains(JWTConstants.HEADER_AUTHENTICATION_BEARER)) {
            token = token.substring(JWTConstants.HEADER_AUTHENTICATION_BEARER.length()+1);
        }
        return token;
    }

    public JWTModel decode(String token) throws JWTDecodingException {
        DecodedJWT decodedJWT;
        try {
            decodedJWT = JWT.decode(this.getToken(token));
        } catch (JWTDecodeException exception) {
            throw new JWTDecodingException();
        }
        JWTAuth0PayloadModel auth0Model = new JWTAuth0PayloadModel();
        auth0Model.setDecodedToken(decodedJWT);
        JWTModel jwtModel = new JWTModel();
        jwtModel.setPayload(auth0Model);
        return jwtModel;
    }
}

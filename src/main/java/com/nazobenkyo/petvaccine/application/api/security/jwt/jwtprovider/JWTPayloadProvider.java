package com.nazobenkyo.petvaccine.application.api.security.jwt.jwtprovider;

import com.nazobenkyo.petvaccine.application.api.security.jwt.jwtprovider.auth0.JWTAuth0PayloadModel;

import java.util.Arrays;
import java.util.List;

import static com.nazobenkyo.petvaccine.application.api.security.jwt.JWTConstants.ROLES_FIELD;

public class JWTPayloadProvider extends JWTAuth0PayloadModel {
    private final JWTModel jwtModel;

    public JWTPayloadProvider(JWTModel jwtModel) {
        this.jwtModel = jwtModel;
    }

    public boolean hasRole(String role) {
        List<String> roles = Arrays.asList(this.jwtModel.getPayload().getClaim(ROLES_FIELD).as(String[].class));
        return roles.contains(role);
    }
}

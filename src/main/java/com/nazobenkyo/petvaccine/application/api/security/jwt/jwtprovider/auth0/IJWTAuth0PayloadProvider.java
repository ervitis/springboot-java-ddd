package com.nazobenkyo.petvaccine.application.api.security.jwt.jwtprovider.auth0;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.Payload;

public interface IJWTAuth0PayloadProvider extends Payload {
    void setDecodedToken(DecodedJWT token);
}

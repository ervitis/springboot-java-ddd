package com.nazobenkyo.petvaccine.application.api.security.jwt.jwtprovider.auth0;

import com.auth0.jwt.algorithms.Algorithm;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class JWTAuth0Algorithm {

    protected Algorithm algorithm;

    public static String secret() {
        return "secret";
    }

    public static Algorithm getAlgorithm() {
        return Algorithm.HMAC256(secret());
    }
}

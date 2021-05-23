package com.nazobenkyo.petvaccine.application.api.security.jwt.jwtprovider.auth0;

import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.nazobenkyo.petvaccine.application.api.security.jwt.jwtprovider.IJWTPayloadProvider;

import java.util.Date;
import java.util.List;
import java.util.Map;

public class JWTAuth0PayloadModel implements IJWTPayloadProvider {
    private DecodedJWT payload;

    @Override
    public String getIssuer() {
        return this.payload.getIssuer();
    }

    @Override
    public String getSubject() {
        return this.payload.getSubject();
    }

    @Override
    public List<String> getAudience() {
        return this.payload.getAudience();
    }

    @Override
    public Date getExpiresAt() {
        return this.payload.getExpiresAt();
    }

    @Override
    public Date getNotBefore() {
        return this.payload.getNotBefore();
    }

    @Override
    public Date getIssuedAt() {
        return this.payload.getIssuedAt();
    }

    @Override
    public String getId() {
        return this.payload.getId();
    }

    @Override
    public Claim getClaim(String name) {
        return this.payload.getClaim(name);
    }

    @Override
    public Map<String, Claim> getClaims() {
        return this.payload.getClaims();
    }

    @Override
    public void setDecodedToken(DecodedJWT token) {
        this.payload = token;
    }
}

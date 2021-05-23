package com.nazobenkyo.petvaccine.application.api.security.jwt.jwtprovider;

public class JWTModel {
    private String jwtValue;
    private String subject;
    private IJWTPayloadProvider payload;

    public JWTModel() {}

    public JWTModel(String jwtValue) {
        this.jwtValue = jwtValue;
    }

    public String getJwtValue() {
        return this.jwtValue;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getSubject() {
        return this.subject;
    }

    public void setPayload(IJWTPayloadProvider payload) {
        this.payload = payload;
    }

    public IJWTPayloadProvider getPayload() {
        return this.payload;
    }
}

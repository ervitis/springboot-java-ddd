package com.nazobenkyo.petvaccine.application.api.exception.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ErrorResponse {

    public String message;

    public int code;

    public String date;
}

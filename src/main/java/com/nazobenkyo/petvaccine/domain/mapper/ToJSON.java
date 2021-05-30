package com.nazobenkyo.petvaccine.domain.mapper;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ToJSON {
    private static final ObjectMapper mapper = new ObjectMapper();

    public static String convertJsonString(Object o) throws JsonProcessingException {
        return mapper.writeValueAsString(o);
    }
}

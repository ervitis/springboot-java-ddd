package com.nazobenkyo.petvaccine.domain.mapper;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class ToByteArray {

    private static final ObjectMapper mapper = new ObjectMapper();

    public static byte[] fromObject(Object obj) throws IOException {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        mapper.enable(SerializationFeature.INDENT_OUTPUT);
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        mapper.writeValue(bos, obj);
        byte[] body = bos.toByteArray();
        bos.flush();
        bos.close();
        return body;
    }
}

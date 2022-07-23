package com.ws.hw1.utils;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

public class ReadFileUtil {
    ObjectMapper objectMapper = new ObjectMapper();

    public <T> T execute(String name, Class<T> valueType) throws IOException {
        return objectMapper.readValue
                                   (getClass().getClassLoader().getResource(name), valueType);
    }
}

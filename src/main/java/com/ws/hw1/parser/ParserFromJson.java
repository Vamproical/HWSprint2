package com.ws.hw1.parser;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.util.Arrays;
import java.util.List;

public class ParserFromJson implements Parser {
    @Override
    public List<EmployeeFromFile> parse(File file) {
        List<EmployeeFromFile> parsed;
        try {
            ObjectMapper mapper = new ObjectMapper();
            parsed = Arrays.asList(mapper.readValue(file, EmployeeFromFile[].class));
        } catch (Exception ex) {
            throw new IllegalArgumentException("The file isn't found");
        }
        return parsed;
    }
}

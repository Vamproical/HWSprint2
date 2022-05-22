package com.ws.hw1.service;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.ws.hw1.model.EmployeeFromFile;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FileService {
    public List<EmployeeFromFile> read(File file) {
        Gson gson = new Gson();
        List<EmployeeFromFile> parsed = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            JsonArray jsonArray =  JsonParser.parseReader(br).getAsJsonArray();
            for (JsonElement element: jsonArray) {
                parsed.add(gson.fromJson(element, EmployeeFromFile.class));
            }
        } catch (IOException e) {
            throw new IllegalArgumentException("The file isn't found");
        }
        return parsed;
    }
}

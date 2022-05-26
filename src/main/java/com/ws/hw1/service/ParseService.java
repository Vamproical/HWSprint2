package com.ws.hw1.service;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import com.ws.hw1.model.EmployeeFromFile;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ParseService {
    public List<EmployeeFromFile> parseJsonFile(File file) {
        List<EmployeeFromFile> parsed;
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            JsonArray jsonArray = JsonParser.parseReader(br).getAsJsonArray();
            Type listType = new TypeToken<ArrayList<EmployeeFromFile>>(){}.getType();
            parsed = new Gson().fromJson(jsonArray, listType);
            parsed.forEach(i -> Collections.sort(i.getCharacteristics()));
        } catch (IOException e) {
            throw new IllegalArgumentException("The file isn't found");
        }
        return parsed;
    }
}

package com.ws.hw1.service;

import com.ws.hw1.model.EmployeeFromFile;

import java.io.File;
import java.util.List;

public interface ParseService {
    List<EmployeeFromFile> parseJsonFile(File file);
}

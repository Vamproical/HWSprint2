package com.ws.hw1.service;

import com.ws.hw1.ExpectedData;
import com.ws.hw1.model.EmployeeFromFile;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.List;

class FileServiceTest {
    private final FileService fileService = new FileService();

    @Test
    void read() {
        List<EmployeeFromFile> expected = List.of(ExpectedData.employeeFromFile, ExpectedData.employeeFromFile1);
        List<EmployeeFromFile> actual = fileService.read(new File("src/main/resources/employees.json"));
        Assertions.assertEquals(expected, actual);
    }

}
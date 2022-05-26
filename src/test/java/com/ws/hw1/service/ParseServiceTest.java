package com.ws.hw1.service;

import com.ws.hw1.model.EmployeeFromFile;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.List;

class ParseServiceTest {
    public static final EmployeeFromFile EMPLOYEE_FROM_FILE = new EmployeeFromFile(
            "Иван",
            "Иванов",
            null,
            List.of("some characteristics"),
            "854ef89d-6c27-4635-926d-894d76a81707");

    public static final EmployeeFromFile EMPLOYEE_FROM_FILE1 = new EmployeeFromFile(
            "Генадий",
            "Кузьмин",
            "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Cras sitamet dictum felis, eu fringilla eros. Sed et gravida neque. Nullam at egestas erat. Mauris vitae convallis nulla. Aenean condimentum lectus magna. Suspendisse viverra quam non ante pellentesque, a euismod nunc dapibus. Duis sed congue erat",
            List.of("honest",
                    "introvert",
                    "like criticism",
                    "love of Learning",
                    "pragmatism"),
            "762d15a5-3bc9-43ef-ae96-02a680a557d0");
    private final ParseService parseService = new ParseService();

    @Test
    void read() {
        List<EmployeeFromFile> expected = List.of(EMPLOYEE_FROM_FILE, EMPLOYEE_FROM_FILE1);
        List<EmployeeFromFile> actual = parseService.parseJsonFile(new File("src/test/resources/employees.json"));
        Assertions.assertEquals(expected, actual);
    }

}
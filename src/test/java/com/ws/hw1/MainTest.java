package com.ws.hw1;

import com.ws.hw1.model.Employee;
import com.ws.hw1.service.EmployeeService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

class MainTest {
    EmployeeService service = new EmployeeService();
    @Test
    void checkTheCorrectReadFile() {
        File file = new File("src/main/resources/employees.json");
        List<String> actual = service.read(file);
        Assertions.assertEquals(DataSetForTest.stringsFromFile, actual);
    }

    @Test
    void checkTheIncorrectReadFile() {
        File file = new File("src/main/resources/employees.json");

        List<String> actual = service.read(file);
        Assertions.assertNotEquals(DataSetForTest.dataForTheTestWithIncorrectReadFile, actual);
    }
    @Test
    void correctParse() {
        Employee expected = new Employee("Геннадий",
                "Кузьмин",
                "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Cras sitamet dictum felis, eu fringilla eros. Sed et gravida neque. Nullam at egestas erat. Mauris vitae convallis nulla. Aenean condimentum lectus magna. Suspendisse viverra quam non ante pellentesque, a euismod nunc dapibus. Duis sed congue erat",
                new ArrayList<>(List.of(" introvert, like criticism, love of Learning, pragmatism,honest".split(","))),
                "Engineer");
        Employee actual = service.parse(DataSetForTest.stringsFromFile.get(0));
        Assertions.assertEquals(expected,actual);
    }

    @Test
    void checkNotNullField() {
        Assertions.assertThrows(NullPointerException.class, () -> service.parse(DataSetForTest.incorrectStringsFromFile.get(0)));
    }
}
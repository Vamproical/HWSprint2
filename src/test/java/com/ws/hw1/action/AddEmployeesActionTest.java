package com.ws.hw1.action;

import com.ws.hw1.mapper.EmployeeMapperImpl;
import com.ws.hw1.model.Employee;
import com.ws.hw1.model.Post;
import com.ws.hw1.service.EmployeeService;
import com.ws.hw1.service.ParseService;
import com.ws.hw1.service.PostService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.List;
import java.util.UUID;

class AddEmployeesActionTest {

    public static final Employee employee = new Employee(
            "Иван",
            "Иванов",
            null,
            List.of("some characteristics"),
            new Post(UUID.fromString("854ef89d-6c27-4635-926d-894d76a81707"), "Tech lead"));

    public static final Employee employee1 = new Employee(
            "Геннадий",
            "Кузьмин",
            "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Cras sitamet dictum felis, eu fringilla eros. Sed et gravida neque. Nullam at egestas erat. Mauris vitae convallis nulla. Aenean condimentum lectus magna. Suspendisse viverra quam non ante pellentesque, a euismod nunc dapibus. Duis sed congue erat",
            List.of("honest",
                    "introvert",
                    "like criticism",
                    "love of Learning",
                    "pragmatism"),
            new Post(UUID.fromString("762d15a5-3bc9-43ef-ae96-02a680a557d0"), "Tech Writer"));

    @Test
    void addEmployeesFromFile() {
        //Arrange
        List<Employee> expected = List.of(employee, employee1);
        EmployeeService employeeService = new EmployeeService();
        AddEmployeesAction action = new AddEmployeesAction(new ParseService(),
                new PostService(),
                employeeService,
                new EmployeeMapperImpl());
        //Act
        action.addEmployeesFromFile(new File("src/test/resources/employees.json"));
        //Assert
        Assertions.assertEquals(expected,employeeService.getEmployees());
    }
}
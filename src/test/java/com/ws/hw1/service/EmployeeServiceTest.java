package com.ws.hw1.service;

import com.ws.hw1.model.Employee;
import com.ws.hw1.model.Post;
import com.ws.hw1.model.SearchParams;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.UUID;

class EmployeeServiceTest {
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

    private final EmployeeService employeeService = new EmployeeService();

    @BeforeEach
    private void addEmployees() {
        employeeService.addEmployees(List.of(employee, employee1));
    }

    @Test
    void getAllOrderedWithoutSearch() {
        List<Employee> excepted = List.of(employee1, employee);
        List<Employee> actual = employeeService.getAllOrdered(new SearchParams(UUID.randomUUID(), null, null, null));
        Assertions.assertEquals(excepted, actual);
    }

    @Test
    void getAllOrderedSearchByFirstName() {
        List<Employee> excepted = List.of(employee1);
        List<Employee> actual = employeeService.getAllOrdered(new SearchParams(UUID.randomUUID(), "Геннадий", null, null));
        Assertions.assertEquals(excepted, actual);
    }

    @Test
    void getAllOrderedSearchByLastName() {
        List<Employee> excepted = List.of(employee);
        List<Employee> actual = employeeService.getAllOrdered(new SearchParams(UUID.randomUUID(), null, "Иванов", null));
        Assertions.assertEquals(excepted, actual);
    }

    @Test
    void getAllOrderedSearchByPostID() {
        List<Employee> excepted = List.of(employee);
        List<Employee> actual = employeeService.getAllOrdered(new SearchParams(UUID.randomUUID(), null, null, UUID.fromString("854ef89d-6c27-4635-926d-894d76a81707")));
        Assertions.assertEquals(excepted, actual);
    }
}
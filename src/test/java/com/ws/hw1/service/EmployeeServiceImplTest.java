package com.ws.hw1.service;

import com.ws.hw1.model.Employee;
import com.ws.hw1.model.Post;
import com.ws.hw1.service.employee.EmployeeServiceImpl;
import com.ws.hw1.service.employee.SearchParams;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.UUID;

class EmployeeServiceImplTest {
    public static final Employee EMPLOYEE = new Employee(
            UUID.randomUUID(),
            "Иван",
            "Иванов",
            null,
            List.of("some characteristics"),
            new Post(UUID.fromString("854ef89d-6c27-4635-926d-894d76a81707"), "Tech lead"));

    public static final Employee EMPLOYEE1 = new Employee(
            UUID.randomUUID(),
            "Геннадий",
            "Кузьмин",
            "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Cras sitamet dictum felis, eu fringilla eros. Sed et gravida neque. Nullam at egestas erat. Mauris vitae convallis nulla. Aenean condimentum lectus magna. Suspendisse viverra quam non ante pellentesque, a euismod nunc dapibus. Duis sed congue erat",
            List.of("honest",
                    "introvert",
                    "like criticism",
                    "love of Learning",
                    "pragmatism"),
            new Post(UUID.fromString("762d15a5-3bc9-43ef-ae96-02a680a557d0"), "Tech Writer"));

    private final EmployeeServiceImpl employeeServiceImpl = new EmployeeServiceImpl();

    @Test
    void getAllOrderedWithoutSearch() {
        List<Employee> excepted = List.of(EMPLOYEE1, EMPLOYEE);
        SearchParams searchParams = new SearchParams(null, null);
        List<Employee> actual = employeeServiceImpl.getAll(searchParams);
        Assertions.assertEquals(excepted, actual);
    }

    @Test
    void getAllOrderedSearchByFirstName() {
        List<Employee> excepted = List.of(EMPLOYEE1);
        SearchParams searchParams = new SearchParams("Геннадий", null);
        List<Employee> actual = employeeServiceImpl.getAll(searchParams);
        Assertions.assertEquals(excepted, actual);
    }

    @Test
    void getAllOrderedSearchByLastName() {
        List<Employee> excepted = List.of(EMPLOYEE);
        SearchParams searchParams = new SearchParams("Иванов", null);
        List<Employee> actual = employeeServiceImpl.getAll(searchParams);
        Assertions.assertEquals(excepted, actual);
    }

    @Test
    void getAllOrderedSearchByPostID() {
        List<Employee> excepted = List.of(EMPLOYEE);
        SearchParams searchParams = new SearchParams(null, UUID.fromString("854ef89d-6c27-4635-926d-894d76a81707"));
        List<Employee> actual = employeeServiceImpl.getAll(searchParams);
        Assertions.assertEquals(excepted, actual);
    }
}
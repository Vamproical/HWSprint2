package com.ws.hw1.service;

import com.ws.hw1.ExpectedData;
import com.ws.hw1.model.Employee;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

class EmployeeServiceTest {
    EmployeeService employeeService = new EmployeeService();

    private void addEmployees() {
        employeeService.addEmployees(List.of(ExpectedData.employee,ExpectedData.employee1));
    }

    @Test
    void getAllOrderedWithoutSearch() {
        List<Employee> excepted = List.of(ExpectedData.employee1, ExpectedData.employee);
        addEmployees();
        List<Employee> actual = employeeService.getAllOrdered(Optional.empty());
        Assertions.assertEquals(excepted,actual);
    }

    @Test
    void getAllOrderedSearchByFirstName() {
        List<Employee> excepted = List.of(ExpectedData.employee1);
        addEmployees();
        List<Employee> actual = employeeService.getAllOrdered(Optional.of("Генадий"));
        Assertions.assertEquals(excepted,actual);
    }

    @Test
    void getAllOrderedSearchByLastName() {
        List<Employee> excepted = List.of(ExpectedData.employee);
        addEmployees();
        List<Employee> actual = employeeService.getAllOrdered(Optional.of("Иванов"));
        Assertions.assertEquals(excepted,actual);
    }

    @Test
    void getAllOrderedSearchByPostName() {
        List<Employee> excepted = List.of(ExpectedData.employee);
        addEmployees();
        List<Employee> actual = employeeService.getAllOrdered(Optional.of("Tech lead"));
        Assertions.assertEquals(excepted,actual);
    }
}
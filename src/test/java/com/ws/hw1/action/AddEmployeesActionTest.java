package com.ws.hw1.action;

import com.ws.hw1.ExpectedData;
import com.ws.hw1.mapper.EmployeeListMapperImpl;
import com.ws.hw1.model.Employee;
import com.ws.hw1.service.EmployeeService;
import com.ws.hw1.service.FileService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;

class AddEmployeesActionTest {

    @Test
    void addEmployeesFromFile() {
        List<Employee> expected = List.of(ExpectedData.employee, ExpectedData.employee1);
        EmployeeService employeeService = new EmployeeService();
        AddEmployeesAction action = new AddEmployeesAction(new FileService(),
                employeeService,
                new EmployeeListMapperImpl());
        action.addEmployeesFromFile("src/main/resources/employees.json");
        Assertions.assertEquals(expected,employeeService.getEmployees());
    }
}
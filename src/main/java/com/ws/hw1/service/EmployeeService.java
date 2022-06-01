package com.ws.hw1.service;

import com.ws.hw1.model.Employee;
import com.ws.hw1.model.SearchParams;

import java.util.List;

public interface EmployeeService {
    void addEmployees(List<Employee> employees);

    List<Employee> getAllOrdered(SearchParams params);
    List<Employee> getEmployees();
}

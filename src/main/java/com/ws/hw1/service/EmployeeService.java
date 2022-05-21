package com.ws.hw1.service;

import com.ws.hw1.model.Employee;

import java.util.*;
import java.util.stream.Collectors;

public class EmployeeService {
    private final List<Employee> employees = new ArrayList<>();

    public void addEmployees(List<Employee> employees) {
        this.employees.addAll(employees);
    }

    public List<Employee> getAllOrdered() {
        return employees.stream()
                .sorted(Comparator.comparing(Employee::getFirstName)
                        .thenComparing(Employee::getLastName))
                .collect(Collectors.toList());
    }
    public void print() {
        getAllOrdered().forEach(System.out::println);
    }
}

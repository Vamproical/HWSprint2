package com.ws.hw1.service;

import com.ws.hw1.model.Employee;

import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class EmployeeService {
    private final List<Employee> employees = new ArrayList<>();

    public void addEmployees(List<Employee> employees) {
        this.employees.addAll(employees);
    }

    public List<Employee> getAllOrdered(Optional<String> search) {
        Predicate<Employee> searchPattern = search.<Predicate<Employee>>map(s -> (i -> i.getFirstName().equals(s) ||
                i.getLastName().equals(s) ||
                i.getPost().getName().equals(s))).orElseGet(() -> Objects::nonNull);
        return employees.stream()
                .filter(searchPattern)
                .sorted(Comparator.comparing(Employee::getFirstName)
                        .thenComparing(Employee::getLastName))
                .collect(Collectors.toList());
    }

    public List<Employee> getEmployees() {
        return employees;
    }
}

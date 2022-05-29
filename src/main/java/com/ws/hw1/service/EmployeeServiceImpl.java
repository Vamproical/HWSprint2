package com.ws.hw1.service;

import com.ws.hw1.model.Employee;
import com.ws.hw1.model.SearchParams;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class EmployeeServiceImpl implements EmployeeService {
    private final List<Employee> employees = new ArrayList<>();

    @Override
    public void addEmployees(List<Employee> employees) {
        this.employees.addAll(employees);
    }

    @Override
    public List<Employee> getAllOrdered(SearchParams params) {
        Predicate<Employee> predicateName = Objects::nonNull;
        Predicate<Employee> predicateUUID = Objects::nonNull;

        if (params.getName() != null) {
            predicateName = employee -> employee.getFirstName().toLowerCase().contains(params.getName().toLowerCase()) ||
                                        employee.getLastName().toLowerCase().contains(params.getName().toLowerCase());
        }
        if (params.getPostId() != null) {
            predicateUUID = employee -> employee.getPost().getId().equals(params.getPostId());
        }

        return employees.stream()
                .filter(predicateName)
                .filter(predicateUUID)
                .sorted(Comparator.comparing(Employee::getFirstName)
                        .thenComparing(Employee::getLastName))
                .collect(Collectors.toList());
    }

    public List<Employee> getEmployees() {
        return employees;
    }
}

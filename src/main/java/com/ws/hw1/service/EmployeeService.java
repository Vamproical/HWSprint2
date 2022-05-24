package com.ws.hw1.service;

import com.ws.hw1.model.Employee;
import com.ws.hw1.model.SearchParams;

import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class EmployeeService {
    private final List<Employee> employees = new ArrayList<>();

    public void addEmployees(List<Employee> employees) {
        this.employees.addAll(employees);
    }

    public List<Employee> getAllOrdered(SearchParams params) {
        Predicate<Employee> predicateFirstName = Objects::nonNull;
        Predicate<Employee> predicateLastName= Objects::nonNull;
        Predicate<Employee> predicateUUID = Objects::nonNull;

        if(params.getFirstName() != null){
            predicateFirstName = employee -> employee.getFirstName().toLowerCase().contains(params.getFirstName().toLowerCase());
        }
        if(params.getLastName() != null){
            predicateLastName = employee ->  employee.getLastName().toLowerCase().contains(params.getLastName().toLowerCase());
        }
        if(params.getPostID() != null){
            predicateUUID = employee -> employee.getPost().getId().equals(params.getPostID());
        }

        return employees.stream()
                .filter(predicateFirstName)
                .filter(predicateLastName)
                .filter(predicateUUID)
                .sorted(Comparator.comparing(Employee::getFirstName)
                        .thenComparing(Employee::getLastName))
                .collect(Collectors.toList());
    }

    public List<Employee> getEmployees() {
        return employees;
    }
}

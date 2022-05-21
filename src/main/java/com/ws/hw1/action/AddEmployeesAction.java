package com.ws.hw1.action;

import com.ws.hw1.mapper.EmployeeListMapper;
import com.ws.hw1.model.Employee;
import com.ws.hw1.model.EmployeeFromFile;
import com.ws.hw1.service.EmployeeService;
import com.ws.hw1.service.FileService;

import java.io.File;
import java.util.Collections;
import java.util.List;

public class AddEmployeesAction {
    private final FileService fileService;
    private final EmployeeService employeeService;
    private final EmployeeListMapper employeeListMapper;

    public AddEmployeesAction(final FileService fileService,
                              final EmployeeService employeeService,
                              final EmployeeListMapper employeeListMapper) {
        this.fileService = fileService;
        this.employeeService = employeeService;
        this.employeeListMapper = employeeListMapper;
    }

    public void addEmployeesFromFile(String fileName) {
        List<EmployeeFromFile> parsed = fileService.read(new File(fileName));
        parsed.forEach(i -> Collections.sort(i.getCharacteristics()));

        List<Employee> employees = employeeListMapper.toEmployee(parsed);
        employeeService.addEmployees(employees);
    }
}

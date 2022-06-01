package com.ws.hw1.action;

import com.ws.hw1.mapper.EmployeeMapper;
import com.ws.hw1.model.Employee;
import com.ws.hw1.model.EmployeeFromFile;
import com.ws.hw1.service.EmployeeService;
import com.ws.hw1.service.ParseService;
import com.ws.hw1.service.PostService;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class AddEmployeesAction {
    private final ParseService parseService;
    private final PostService postService;
    private final EmployeeService employeeService;
    private final EmployeeMapper employeeMapper;

    public AddEmployeesAction(final ParseService fileService,
                              final PostService postService,
                              final EmployeeService employeeService,
                              final EmployeeMapper employeeMapper) {
        this.parseService = fileService;
        this.postService = postService;
        this.employeeService = employeeService;
        this.employeeMapper = employeeMapper;
    }

    public void addEmployeesFromFile(File file) {
        List<EmployeeFromFile> parsed = parseService.parseJsonFile(file);
        List<Employee> employees = new ArrayList<>();
        for (EmployeeFromFile fromFile : parsed) {
            employees.add(employeeMapper.toEmployee(fromFile, postService.getPost(fromFile.getPostId())));
        }
        employeeService.addEmployees(employees);
    }
}

package com.ws.hw1.action;

import com.ws.hw1.mapper.EmployeeMapper;
import com.ws.hw1.model.Employee;
import com.ws.hw1.parser.EmployeeFromFile;
import com.ws.hw1.parser.Parser;
import com.ws.hw1.service.employee.EmployeeService;
import com.ws.hw1.service.post.PostService;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class AddEmployeesAction {
    private final Parser parseService;
    private final PostService postService;
    private final EmployeeService employeeService;
    private final EmployeeMapper employeeMapper;

    public AddEmployeesAction(final Parser fileService,
                              final PostService postService,
                              final EmployeeService employeeService,
                              final EmployeeMapper employeeMapper) {
        this.parseService = fileService;
        this.postService = postService;
        this.employeeService = employeeService;
        this.employeeMapper = employeeMapper;
    }

    public void execute(File file) {
        List<EmployeeFromFile> parsed = parseService.parse(file);
        List<Employee> employees = new ArrayList<>();
        for (EmployeeFromFile fromFile : parsed) {
            //employees.add(employeeMapper.toEmployee(fromFile, postService.getPost(fromFile.getPostId())));
        }
        //employeeService.addEmployees(employees);
    }
}

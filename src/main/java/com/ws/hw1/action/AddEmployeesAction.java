package com.ws.hw1.action;

import com.ws.hw1.mapper.EmployeeMapper;
import com.ws.hw1.model.Employee;
import com.ws.hw1.model.EmployeeFromFile;
import com.ws.hw1.model.Post;
import com.ws.hw1.service.*;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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
        List<Post> posts = convert(parsed);
        List<Employee> employees = new ArrayList<>();
        for (int i = 0; i < parsed.size(); i++) {
            employees.add(employeeMapper.toEmployee(parsed.get(i),posts.get(i)));
        }
        employeeService.addEmployees(employees);
    }

    private List<Post> convert(List<EmployeeFromFile> employees) {
        return employees.stream()
                .map(i -> postService.convert(i.getPostId()))
                .collect(Collectors.toList());
    }
}

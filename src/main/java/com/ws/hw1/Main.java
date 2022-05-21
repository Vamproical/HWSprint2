package com.ws.hw1;

import com.ws.hw1.action.AddEmployeesAction;
import com.ws.hw1.mapper.EmployeeListMapperImpl;
import com.ws.hw1.service.EmployeeService;
import com.ws.hw1.service.FileService;

public class Main {
    public static void main(String[] args) {
        if (args.length < 1) {
            throw new IllegalArgumentException("File must be the argument of the program");
        }
        String fileName = args[0];
        EmployeeService employeeService = new EmployeeService();
        AddEmployeesAction addEmployeesAction = new AddEmployeesAction(new FileService(),
                employeeService,
                new EmployeeListMapperImpl());

        addEmployeesAction.addEmployeesFromFile(fileName);

        employeeService.print();
    }
}

package com.ws.hw1;

import com.ws.hw1.action.AddEmployeesAction;
import com.ws.hw1.mapper.EmployeeListMapperImpl;
import com.ws.hw1.service.EmployeeService;
import com.ws.hw1.service.FileService;

import java.util.Optional;

public class Main {
    private static String FILE_NAME = "";
    private static String SEARCH_STRING = null;

    public static void main(String[] args) {
        parseArgs(args);

        EmployeeService employeeService = new EmployeeService();
        AddEmployeesAction addEmployeesAction = new AddEmployeesAction(new FileService(),
                employeeService,
                new EmployeeListMapperImpl());

        addEmployeesAction.addEmployeesFromFile(FILE_NAME);
        employeeService.getAllOrdered(Optional.ofNullable(SEARCH_STRING)).forEach(System.out::println);
    }

    private static void parseArgs(String[] args) {
        if (args.length == 0) {
            throw new IllegalArgumentException("File must be the argument of the program");
        }
        for (int i = 0; i < args.length; i++) {
            if (args[i].equals("-file")) {
                FILE_NAME = args[i + 1];
            }
            if (args[i].equals("-search")) {
                SEARCH_STRING = args[i + 1];
            }
        }
    }
}

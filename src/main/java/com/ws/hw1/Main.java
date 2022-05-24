package com.ws.hw1;

import com.ws.hw1.action.AddEmployeesAction;
import com.ws.hw1.mapper.EmployeeMapperImpl;
import com.ws.hw1.model.ArgsModel;
import com.ws.hw1.model.SearchParams;
import com.ws.hw1.service.EmployeeService;
import com.ws.hw1.service.ParseService;
import com.ws.hw1.service.PostService;

import java.io.File;
import java.util.UUID;

public class Main {
    private static final ArgsModel argsModel = new ArgsModel();

    public static void main(String[] args) {
        parseArgs(args);

        EmployeeService employeeService = new EmployeeService();
        AddEmployeesAction addEmployeesAction = new AddEmployeesAction(new ParseService(),
                new PostService(),
                employeeService,
                new EmployeeMapperImpl());

        addEmployeesAction.addEmployeesFromFile(new File(argsModel.getPath()));
        employeeService.getAllOrdered(argsModel.getSearchParams()).forEach(System.out::println);
    }

    private static void parseArgs(String[] args) {

        if (args.length == 0) {
            throw new IllegalArgumentException("File must be the argument of the program");
        }

        SearchParams.SearchParamsBuilder searchParams = SearchParams.builder();
        for (int i = 0; i < args.length; i++) {

            if (args[i].equals("-file")) {
                argsModel.setPath(args[i + 1]);
            }
            if (args[i].equals("-searchByFirstName")) {
                searchParams.firstName(args[i + 1]);
            }
            if (args[i].equals("-searchByLastName")) {
                searchParams.lastName(args[i + 1]);
            }
            if (args[i].equals("-searchByPostID")) {
                searchParams.postID(UUID.fromString(args[i + 1]));
            }
        }
        argsModel.setSearchParams(searchParams.build());
    }
}

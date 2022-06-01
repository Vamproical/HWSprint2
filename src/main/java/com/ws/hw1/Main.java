package com.ws.hw1;

import com.ws.hw1.action.AddEmployeesAction;
import com.ws.hw1.mapper.EmployeeMapper;
import com.ws.hw1.model.ArgsModel;
import com.ws.hw1.model.SearchParams;
import com.ws.hw1.service.*;
import org.mapstruct.factory.Mappers;

import java.io.File;
import java.util.UUID;

public class Main {
    public static void main(String[] args) {
        ArgsModel argsModel = parseArgs(args);

        EmployeeService employeeServiceImpl = new EmployeeServiceImpl();
        AddEmployeesAction addEmployeesAction = new
                AddEmployeesAction(new ParseServiceImpl(),
                                   new PostServiceImpl(),
                employeeServiceImpl,
                Mappers.getMapper(EmployeeMapper.class));

        addEmployeesAction.addEmployeesFromFile(new File(argsModel.getPath()));
        employeeServiceImpl.getAllOrdered(argsModel.getSearchParams()).forEach(System.out::println);
    }

    private static ArgsModel parseArgs(String[] args) {
        if (args.length == 0) {
            throw new IllegalArgumentException("File must be the argument of the program");
        }
        ArgsModel argsModel = new ArgsModel();

        SearchParams.SearchParamsBuilder searchParams = SearchParams.builder();
        for (int i = 0; i < args.length; i++) {

            if (args[i].equals("-file")) {
                argsModel.setPath(args[i + 1]);
            }
            if (args[i].equals("-searchByName")) {
                searchParams.name(args[i + 1]);
            }
            if (args[i].equals("-searchByPostID")) {
                searchParams.postId(UUID.fromString(args[i + 1]));
            }
        }
        argsModel.setSearchParams(searchParams.build());
        return argsModel;
    }
}

package com.ws.hw1.mapper;

import com.ws.hw1.model.Employee;
import com.ws.hw1.model.EmployeeFromFile;
import com.ws.hw1.service.PostService;
import org.mapstruct.Mapper;

import java.util.ArrayList;
import java.util.List;

@Mapper
public interface EmployeeMapper {

    default Employee toEmployee(EmployeeFromFile fromFile) {
        PostService postService = new PostService();

        Employee.EmployeeBuilder employee = Employee.builder();
        employee.firstName(fromFile.getFirstName());
        employee.lastName(fromFile.getLastName());
        employee.description(fromFile.getDescription());
        List<String> list = fromFile.getCharacteristics();
        employee.characteristics(new ArrayList<>(list));
        employee.post(postService.convert(fromFile.getPostID()));

        return employee.build();
    }

}

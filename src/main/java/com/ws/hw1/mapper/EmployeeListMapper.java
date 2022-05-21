package com.ws.hw1.mapper;

import com.ws.hw1.model.Employee;
import com.ws.hw1.model.EmployeeFromFile;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(uses = EmployeeMapper.class)
public interface EmployeeListMapper {

    List<Employee> toEmployee(List<EmployeeFromFile> fromFileList);

}

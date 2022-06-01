package com.ws.hw1.mapper;

import com.ws.hw1.model.Employee;
import com.ws.hw1.model.EmployeeFromFile;
import com.ws.hw1.model.Post;
import org.mapstruct.Mapper;

@Mapper
public interface EmployeeMapper {
    Employee toEmployee(EmployeeFromFile fromFile, Post post);
}

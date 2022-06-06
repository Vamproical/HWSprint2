package com.ws.hw1.mapper;

import com.ws.hw1.controller.dto.employee.EmployeeDto;
import com.ws.hw1.model.Employee;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface EmployeeMapper {
    EmployeeDto toDTO(Employee employee);
}

package com.ws.hw1.controller.employee.mapper;

import com.ws.hw1.controller.employee.dto.CreateEmployeeDto;
import com.ws.hw1.controller.employee.dto.EmployeeDto;
import com.ws.hw1.controller.employee.dto.SearchParamsDto;
import com.ws.hw1.controller.employee.dto.UpdateEmployeeDto;
import com.ws.hw1.model.Employee;
import com.ws.hw1.model.Post;
import com.ws.hw1.service.argument.CreateEmployeeArgument;
import com.ws.hw1.service.argument.UpdateEmployeeArgument;
import com.ws.hw1.service.employee.SearchParams;
import org.mapstruct.Mapper;

@Mapper
public interface EmployeeMapper {
    EmployeeDto toDTO(Employee employee);

    CreateEmployeeArgument toArgument(CreateEmployeeDto createEmployeeDto, Post post);

    SearchParams toParams(SearchParamsDto dto);

    UpdateEmployeeArgument toUpdateArgument(UpdateEmployeeDto updateEmployeeDto, Post post);
}

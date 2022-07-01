package com.ws.hw1.controller.employee.mapper;

import com.ws.hw1.controller.employee.dto.ContactsDto;
import com.ws.hw1.controller.employee.dto.CreateEmployeeDto;
import com.ws.hw1.controller.employee.dto.EmployeeDto;
import com.ws.hw1.controller.employee.dto.UpdateEmployeeDto;
import com.ws.hw1.model.Contacts;
import com.ws.hw1.model.Employee;
import com.ws.hw1.model.Post;
import com.ws.hw1.service.argument.CreateEmployeeArgument;
import com.ws.hw1.service.argument.UpdateEmployeeArgument;
import org.mapstruct.Mapper;

import java.util.UUID;

@Mapper
public interface EmployeeMapper {
    EmployeeDto toDTO(Employee employee);

    ContactsDto toDTO(Contacts contacts);

    CreateEmployeeDto toCreateDTO(Employee employee, UUID postId);

    Contacts toModel(ContactsDto contacts);

    CreateEmployeeArgument toArgument(CreateEmployeeDto createEmployeeDto, Post post);

    UpdateEmployeeArgument toUpdateArgument(UpdateEmployeeDto updateEmployeeDto, Post post);
}

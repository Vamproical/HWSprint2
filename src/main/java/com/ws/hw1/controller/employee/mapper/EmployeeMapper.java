package com.ws.hw1.controller.employee.mapper;

import com.ws.hw1.controller.employee.dto.ContactsDto;
import com.ws.hw1.controller.employee.dto.EmployeeDto;
import com.ws.hw1.model.Contacts;
import com.ws.hw1.model.Employee;
import org.mapstruct.Mapper;

@Mapper
public interface EmployeeMapper {
    EmployeeDto toDTO(Employee employee);

    Contacts toModel(ContactsDto contacts);
}

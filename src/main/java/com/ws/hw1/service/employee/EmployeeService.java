package com.ws.hw1.service.employee;

import com.ws.hw1.model.Employee;
import com.ws.hw1.service.argument.CreateEmployeeArgument;

import java.util.List;
import java.util.UUID;

public interface EmployeeService {

    Employee create(CreateEmployeeArgument employeeArgument);

    Employee update(UUID id, CreateEmployeeArgument employeeArgument);

    void delete(UUID id);

    Employee get(UUID id);

    List<Employee> getAll(SearchParams searchParams);
}

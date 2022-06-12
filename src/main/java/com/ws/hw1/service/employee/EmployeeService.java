package com.ws.hw1.service.employee;

import com.ws.hw1.model.Employee;
import com.ws.hw1.service.argument.CreateEmployeeArgument;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.UUID;

public interface EmployeeService {

    Employee create(@NotNull CreateEmployeeArgument employeeArgument);

    Employee update(@NotNull UUID id, @NotNull CreateEmployeeArgument employeeArgument);

    void delete(@NotNull UUID id);

    Employee get(@NotNull UUID id);

    List<Employee> getAll(@NotNull SearchParams searchParams);
}

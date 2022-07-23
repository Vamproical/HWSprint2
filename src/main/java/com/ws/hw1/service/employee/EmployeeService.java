package com.ws.hw1.service.employee;

import com.ws.hw1.model.Employee;
import com.ws.hw1.service.argument.CreateEmployeeArgument;
import com.ws.hw1.service.argument.UpdateEmployeeArgument;
import lombok.NonNull;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

public interface EmployeeService {

    @Transactional
    Employee create(@NonNull CreateEmployeeArgument employeeArgument);

    @Transactional
    Employee update(@NonNull UUID id, @NonNull UpdateEmployeeArgument employeeArgument);

    @Transactional
    void delete(@NonNull UUID id);

    @Transactional
    Employee getExisting(@NonNull UUID id);

    @Transactional
    List<Employee> getAll(@NonNull SearchParams searchParams);
}

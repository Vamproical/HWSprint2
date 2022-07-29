package com.ws.hw1.service.employee;

import com.ws.hw1.model.Employee;
import com.ws.hw1.service.argument.CreateEmployeeArgument;
import com.ws.hw1.service.argument.UpdateEmployeeArgument;
import lombok.NonNull;
import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.UUID;

public interface EmployeeService {

    Employee create(@NonNull CreateEmployeeArgument employeeArgument);

    Employee update(@NonNull UUID id, @NonNull UpdateEmployeeArgument employeeArgument);

    void delete(@NonNull UUID id);

    Employee getExisting(@NonNull UUID id);

    List<Employee> getAll(@NonNull SearchParams params, Sort sort);
}

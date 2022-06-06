package com.ws.hw1.service.employee;

import com.ws.hw1.exceptionHandler.exception.NotFoundException;
import com.ws.hw1.model.Employee;
import com.ws.hw1.service.argument.CreateEmployeeArgument;
import lombok.NonNull;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Service
public class EmployeeServiceImpl implements EmployeeService {
    private final Map<UUID, Employee> employees = new HashMap<>();

    @Override
    public Employee create(CreateEmployeeArgument employeeArgument) {
        UUID id = UUID.randomUUID();
        Employee employee = createEmployee(id, employeeArgument);
        employees.put(id, employee);
        return employee;
    }

    @Override
    public Employee update(@NonNull UUID id, CreateEmployeeArgument employeeArgument) {
        throwExceptionIfNotExits(id);
        Employee employee = createEmployee(id, employeeArgument);
        employees.replace(id, employee);
        return employee;
    }

    @Override
    public void delete(UUID id) {
        throwExceptionIfNotExits(id);
        employees.remove(id);
    }

    @Override
    public Employee get(UUID id) {
        throwExceptionIfNotExits(id);
        return employees.get(id);
    }

    @Override
    public List<Employee> getAll(SearchParams params) {
        Predicate<Employee> predicate = (x) -> true;

        if (params.getName() != null) {
            predicate = predicate.and(employee -> employee.getFirstName().toLowerCase().contains(params.getName().toLowerCase()) ||
                    employee.getLastName().toLowerCase().contains(params.getName().toLowerCase()));
        }
        if (params.getPostId() != null) {
            predicate = predicate.and(employee -> employee.getPost().getId().equals(params.getPostId()));
        }

        return employees.values()
                        .stream()
                        .filter(predicate)
                        .sorted(Comparator.comparing(Employee::getFirstName)
                                          .thenComparing(Employee::getLastName))
                        .collect(Collectors.toList());
    }

    private void throwExceptionIfNotExits(UUID id) {
        if (!employees.containsKey(id)) {
            throw new NotFoundException();
        }
    }

    private Employee createEmployee(UUID id, CreateEmployeeArgument employeeArgument) {
        return new Employee(id,
                employeeArgument.getFirstName(),
                employeeArgument.getLastName(),
                employeeArgument.getDescription(),
                employeeArgument.getCharacteristics(),
                employeeArgument.getPost());
    }
}

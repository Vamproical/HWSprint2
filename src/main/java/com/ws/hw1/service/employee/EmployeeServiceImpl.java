package com.ws.hw1.service.employee;

import com.ws.hw1.model.Employee;
import com.ws.hw1.service.argument.CreateEmployeeArgument;
import com.ws.hw1.service.argument.UpdateEmployeeArgument;
import com.ws.hw1.utils.Guard;
import lombok.NonNull;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Service
public class EmployeeServiceImpl implements EmployeeService {
    private final Map<UUID, Employee> employees = new HashMap<>();

    @Override
    public Employee create(@NonNull CreateEmployeeArgument employeeArgument) {
        UUID id = UUID.randomUUID();
        Employee employee = Employee.builder()
                                    .id(id)
                                    .firstName(employeeArgument.getFirstName())
                                    .lastName(employeeArgument.getLastName())
                                    .characteristics(employeeArgument.getCharacteristics())
                                    .contacts(employeeArgument.getContacts())
                                    .description(employeeArgument.getDescription())
                                    .jobType(employeeArgument.getJobType())
                                    .post(employeeArgument.getPost())
                                    .build();

        employees.put(id, employee);

        return employee;
    }

    @Override
    public Employee update(@NonNull UUID id, @NonNull UpdateEmployeeArgument employeeArgument) {
        Employee employee = getExisting(id);
        employee.setFirstName(employeeArgument.getFirstName());
        employee.setLastName(employeeArgument.getLastName());
        employee.setDescription(employeeArgument.getDescription());
        employee.setCharacteristics(employeeArgument.getCharacteristics());
        employee.setContacts(employeeArgument.getContacts());
        employee.setPost(employeeArgument.getPost());
        employee.setJobType(employeeArgument.getJobType());

        employees.replace(id, employee);

        return employee;
    }

    @Override
    public void delete(@NonNull UUID id) {
        Guard.check(employees.containsKey(id), "The employee not found");
        employees.remove(id);
    }

    @Override
    public Employee getExisting(@NonNull UUID id) {
        Guard.check(employees.containsKey(id), "The employee not found");
        return employees.get(id);
    }

    @Override
    public List<Employee> getAll(@NonNull SearchParams params) {
        Predicate<Employee> predicate = filter(params);

        return employees.values()
                        .stream()
                        .filter(predicate)
                        .sorted(Comparator.comparing(Employee::getFirstName)
                                          .thenComparing(Employee::getLastName))
                        .collect(Collectors.toList());
    }

    private Predicate<Employee> filter(SearchParams params) {
        Predicate<Employee> predicate = x -> true;

        if (params.getName() != null) {
            predicate = predicate.and(employee -> employee.getFirstName().toLowerCase().contains(params.getName().toLowerCase()) ||
                    employee.getLastName().toLowerCase().contains(params.getName().toLowerCase()));
        }

        if (params.getPostId() != null) {
            predicate = predicate.and(employee -> employee.getPost().getId().equals(params.getPostId()));
        }

        return predicate;
    }
}

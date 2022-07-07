package com.ws.hw1.service.employee;

import com.ws.hw1.exceptionhandler.exception.NotFoundException;
import com.ws.hw1.model.Employee;
import com.ws.hw1.repository.EmployeeRepository;
import com.ws.hw1.service.argument.CreateEmployeeArgument;
import com.ws.hw1.service.argument.UpdateEmployeeArgument;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.UUID;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {
    private final EmployeeRepository repository;


    @Override
    public Employee create(@NonNull CreateEmployeeArgument employeeArgument) {
        Employee employee = Employee.builder()
                                    .firstName(employeeArgument.getFirstName())
                                    .lastName(employeeArgument.getLastName())
                                    .characteristics(employeeArgument.getCharacteristics())
                                    .contacts(employeeArgument.getContacts())
                                    .description(employeeArgument.getDescription())
                                    .jobType(employeeArgument.getJobType())
                                    .post(employeeArgument.getPost())
                                    .build();

        return repository.save(employee);
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

        return repository.save(employee);
    }

    @Override
    public void delete(@NonNull UUID id) {
        repository.deleteById(id);
    }

    @Override
    public Employee getExisting(@NonNull UUID id) {
        return repository.findById(id).orElseThrow(() -> new NotFoundException("The employee not found"));
    }

    @Override
    public List<Employee> getAll(@NonNull SearchParams params) {
        Predicate<Employee> predicate = filter(params);

        return repository.findAll()
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

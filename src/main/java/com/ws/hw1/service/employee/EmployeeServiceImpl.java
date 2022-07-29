package com.ws.hw1.service.employee;

import com.google.common.collect.Lists;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.ExpressionUtils;
import com.querydsl.core.types.Predicate;
import com.ws.hw1.exceptionhandler.exception.NotFoundException;
import com.ws.hw1.model.Employee;
import com.ws.hw1.model.QEmployee;
import com.ws.hw1.repository.EmployeeRepository;
import com.ws.hw1.service.argument.CreateEmployeeArgument;
import com.ws.hw1.service.argument.UpdateEmployeeArgument;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {
    private final EmployeeRepository repository;
    private static final QEmployee qEmployee = QEmployee.employee;


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
    @Transactional(isolation = Isolation.READ_COMMITTED)
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
    @Transactional
    public void delete(@NonNull UUID id) {
        getExisting(id);
        repository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Employee getExisting(@NonNull UUID id) {
        return repository.findById(id)
                         .orElseThrow(() -> new NotFoundException("The employee not found"));
    }

    @Override
    @Transactional(readOnly = true)
    public List<Employee> getAll(@NonNull SearchParams params, Sort sort) {
        Predicate filter = filter(params);

        return Lists.newArrayList(repository.findAll(filter, sort));
    }

    private Predicate filter(SearchParams params) {
        BooleanBuilder booleanBuilder = new BooleanBuilder();

        if (params.getName() != null) {
            booleanBuilder.and(qEmployee.firstName
                                       .containsIgnoreCase(params.getName())
                                       .or(qEmployee.lastName
                                                   .containsIgnoreCase(params.getName())));
        }
        if (params.getPostId() != null) {
            booleanBuilder.and(qEmployee.post
                                       .id
                                       .eq(params.getPostId()));
        }
        return ExpressionUtils.allOf(booleanBuilder);
    }
}

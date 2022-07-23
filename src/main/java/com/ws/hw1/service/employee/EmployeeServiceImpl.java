package com.ws.hw1.service.employee;

import com.querydsl.jpa.impl.JPAQuery;
import com.ws.hw1.action.CreateJpaQueryAction;
import com.ws.hw1.exceptionhandler.exception.NotFoundException;
import com.ws.hw1.model.Employee;
import com.ws.hw1.model.QEmployee;
import com.ws.hw1.repository.EmployeeRepository;
import com.ws.hw1.service.argument.CreateEmployeeArgument;
import com.ws.hw1.service.argument.UpdateEmployeeArgument;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {
    private final EmployeeRepository repository;
    private final CreateJpaQueryAction jpaQueryAction;


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
    @Transactional
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
    public List<Employee> getAll(@NonNull SearchParams params) {
        JPAQuery<Employee> query = jpaQueryAction.execute();
        QEmployee employee = QEmployee.employee;

        query.select(employee).from(employee);

        filter(params, query, employee);

        return query.orderBy(employee.lastName.asc(),
                             employee.firstName.asc())
                    .fetch();
    }

    private void filter(SearchParams params, JPAQuery<Employee> query, QEmployee employee) {
        if (params.getName() != null && !params.getName().isEmpty()) {
            query.where(
                    employee.firstName.containsIgnoreCase(params.getName()).or(
                            employee.lastName.containsIgnoreCase(params.getName()))
                       );
        }
        if (params.getPostId() != null) {
            query.where(
                    employee.post.id.eq(params.getPostId())
                       );
        }
    }
}

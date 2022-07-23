package com.ws.hw1.logging;

import com.ws.hw1.model.Employee;
import com.ws.hw1.service.argument.UpdateEmployeeArgument;
import com.ws.hw1.service.employee.EmployeeService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Objects;
import java.util.UUID;

@Aspect
@Component
@Slf4j
@AllArgsConstructor
@ConditionalOnProperty(prefix = "log", name = "update")
public class LoggingUpdatingEmployee {
    private final EmployeeService employeeService;

    @Pointcut("execution(public * com.ws.hw1.service.employee.EmployeeService.update(..))")
    public void update() {
    }


    @Before(value = "update() && " +
                    "args(id, updatedEmployeeArgument)", argNames = "id, updatedEmployeeArgument")
    private void saveLog(UUID id, UpdateEmployeeArgument updatedEmployeeArgument) {
        log.info("Updating employee with id: {}", id);

        Employee employee = employeeService.getExisting(id);

        updatingFields(employee, updatedEmployeeArgument);
    }

    private void updatingFields(Employee employee, UpdateEmployeeArgument updatedEmployeeArgument) {

        if (!Objects.equals(employee.getFirstName(), updatedEmployeeArgument.getFirstName())) {
            log.info("firstName: FROM [{}] TO [{}]", employee.getFirstName(), updatedEmployeeArgument.getFirstName());
        }

        if (!Objects.equals(employee.getLastName(), updatedEmployeeArgument.getLastName())) {
            log.info("lastName: FROM [{}] TO [{}]", employee.getLastName(), updatedEmployeeArgument.getLastName());
        }

        if (!Objects.equals(employee.getDescription(), updatedEmployeeArgument.getDescription())) {
            log.info("description: FROM [{}] TO [{}]", employee.getDescription(), updatedEmployeeArgument.getDescription());
        }

        if (!Objects.equals(employee.getCharacteristics(), updatedEmployeeArgument.getCharacteristics())) {
            log.info("characteristics: FROM [{}] TO [{}]", employee.getCharacteristics(), updatedEmployeeArgument.getCharacteristics());
        }

        if (!Objects.equals(employee.getContacts(), updatedEmployeeArgument.getContacts())) {
            log.info("contacts: FROM [{}] TO [{}]", employee.getContacts(), updatedEmployeeArgument.getContacts());
        }

        if (!Objects.equals(employee.getJobType(), updatedEmployeeArgument.getJobType())) {
            log.info("jobType: FROM [{}] TO [{}]", employee.getJobType(), updatedEmployeeArgument.getJobType());
        }

        if (!Objects.equals(employee.getPost(), updatedEmployeeArgument.getPost())) {
            log.info("post: FROM [{}] TO [{}]", employee.getPost(), updatedEmployeeArgument.getPost());
        }
    }
}

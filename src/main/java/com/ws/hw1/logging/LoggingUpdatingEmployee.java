package com.ws.hw1.logging;

import com.ws.hw1.model.Employee;
import com.ws.hw1.service.argument.UpdateEmployeeArgument;
import com.ws.hw1.service.employee.EmployeeService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

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
        StringBuilder updateLog = new StringBuilder();

        updateLog.append("Updating employee with id: ")
                 .append(id);

        Employee employee = employeeService.getExisting(id);

        updateLog.append(updatingFields(employee, updatedEmployeeArgument));

        log.info(updateLog.toString());
    }

    private StringBuilder updatingFields(Employee employee, UpdateEmployeeArgument updatedEmployeeArgument) {
        StringBuilder result = new StringBuilder();

        if (!Objects.equals(employee.getFirstName(), updatedEmployeeArgument.getFirstName())) {
            result.append("firstName: FROM [")
                  .append(employee.getFirstName())
                  .append("] ")
                  .append("TO [")
                  .append(updatedEmployeeArgument.getFirstName())
                  .append("] ");
        }

        if (!Objects.equals(employee.getLastName(), updatedEmployeeArgument.getLastName())) {
            result.append("lastName: FROM [")
                  .append(employee.getLastName())
                  .append("] ")
                  .append("TO [")
                  .append(updatedEmployeeArgument.getLastName())
                  .append("] ");
        }

        if (!Objects.equals(employee.getDescription(), updatedEmployeeArgument.getDescription())) {
            result.append("description: FROM [")
                  .append(employee.getDescription())
                  .append("] ")
                  .append("TO [")
                  .append(updatedEmployeeArgument.getDescription())
                  .append("] ");
        }

        if (!Objects.equals(employee.getCharacteristics(), updatedEmployeeArgument.getCharacteristics())) {
            result.append("characteristics: FROM [")
                  .append(employee.getCharacteristics())
                  .append("] ")
                  .append("TO [")
                  .append(updatedEmployeeArgument.getCharacteristics())
                  .append("] ");
        }

        if (!Objects.equals(employee.getContacts(), updatedEmployeeArgument.getContacts())) {
            result.append("contacts: FROM [")
                  .append(employee.getContacts())
                  .append("] ")
                  .append("TO [")
                  .append(updatedEmployeeArgument.getContacts())
                  .append("] ");
        }

        if (!Objects.equals(employee.getJobType(), updatedEmployeeArgument.getJobType())) {
            result.append("jobType: FROM [")
                  .append(employee.getJobType())
                  .append("] ")
                  .append("TO [")
                  .append(updatedEmployeeArgument.getJobType())
                  .append("] ");
        }

        if (!Objects.equals(employee.getPost(), updatedEmployeeArgument.getPost())) {
            result.append("post: FROM [")
                  .append(employee.getPost())
                  .append("] ")
                  .append("TO [")
                  .append(updatedEmployeeArgument.getPost())
                  .append("] ");
        }

        return result;
    }
}

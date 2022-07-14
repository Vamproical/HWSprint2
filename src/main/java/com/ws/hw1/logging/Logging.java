package com.ws.hw1.logging;

import com.ws.hw1.action.UpdateEmployeeArgumentAction;
import com.ws.hw1.controller.employee.dto.UpdateEmployeeDto;
import com.ws.hw1.model.Employee;
import com.ws.hw1.service.argument.UpdateEmployeeArgument;
import com.ws.hw1.service.employee.EmployeeService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.LocalVariableTableParameterNameDiscoverer;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.testcontainers.shaded.org.apache.commons.lang3.builder.Diff;
import org.testcontainers.shaded.org.apache.commons.lang3.builder.DiffResult;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.UUID;

@Aspect
@Component
@Slf4j
@AllArgsConstructor
public class Logging {
    private final EmployeeService employeeService;
    private final UpdateEmployeeArgumentAction action;

    @Pointcut("within(com.ws.hw1.controller..*) && !within(com.ws.hw1.controller.employee.mapper..*) && !within(com.ws.hw1.controller.post.mapper..*)")
    public void applicationPackagePointcut() {
    }

    @AfterThrowing(pointcut = "applicationPackagePointcut()", throwing = "e")
    public void logAfterThrowing(JoinPoint joinPoint, Throwable e) {
        log.error("Exception in {}.{}() with message = {}", joinPoint.getSignature().getDeclaringTypeName(),
                  joinPoint.getSignature().getName(), e.getMessage() != null ? e.getMessage() : "NULL");
    }

    @Around("applicationPackagePointcut()")
    public Object around(ProceedingJoinPoint point) throws Throwable {
        Object result;

        try {
            saveLog(point);
            result = point.proceed();
        } catch (IllegalArgumentException e) {
            log.error("Illegal argument: {} in {}.{}()", Arrays.toString(point.getArgs()),
                      point.getSignature().getDeclaringTypeName(), point.getSignature().getName());
            throw e;
        }

        return result;
    }

    private void saveLog(ProceedingJoinPoint joinPoint) {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();

        LogEntity applicationLog = new LogEntity();

        String className = joinPoint.getTarget().getClass().getName();
        String methodName = signature.getName();

        applicationLog.setMethod(className + "." + methodName + "()");

        Object[] args = joinPoint.getArgs();
        LocalVariableTableParameterNameDiscoverer nameDiscoverer = new LocalVariableTableParameterNameDiscoverer();
        String[] paramNames = nameDiscoverer.getParameterNames(method);

        StringBuilder params = new StringBuilder();

        if (methodName.equals("update") && paramNames[1].contains("employeeDto")) {
            params = getUpdatedEmployeeFields(args);
        } else {
            if (args != null && paramNames != null) {
                for (int i = 0; i < args.length; i++) {
                    params.append("  ")
                          .append(paramNames[i])
                          .append(": ")
                          .append(args[i]);
                }
            }
        }

        applicationLog.setParams(params.toString());

        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();

        applicationLog.setIpAddress(request.getRemoteAddr());
        applicationLog.setEndPoint(request.getServletPath());
        applicationLog.setRequestTime(LocalDateTime.now());
        applicationLog.setOperation(request.getMethod());

        log.info(applicationLog.toString());
    }

    private StringBuilder getUpdatedEmployeeFields(Object[] fields) {
        StringBuilder result = new StringBuilder();

        Employee oldEmployee = employeeService.getExisting((UUID) fields[0]);
        UpdateEmployeeArgument argument = action.execute((UpdateEmployeeDto) fields[1]);
        Employee updatedEmployee = Employee.builder()
                                           .id(oldEmployee.getId())
                                           .firstName(argument.getFirstName())
                                           .lastName(argument.getLastName())
                                           .description(argument.getDescription())
                                           .characteristics(argument.getCharacteristics())
                                           .contacts(argument.getContacts())
                                           .post(argument.getPost())
                                           .jobType(argument.getJobType())
                                           .build();

        DiffResult<Employee> diff = oldEmployee.diff(updatedEmployee);
        for (Diff<?> d : diff.getDiffs()) {
            result.append(d.getFieldName());
            result.append("= FROM[");
            result.append(d.getLeft());
            result.append("] TO [");
            result.append(d.getRight());
            result.append("]\n");
        }

        return result;
    }
}

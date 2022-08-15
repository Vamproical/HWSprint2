package com.ws.hw1.logging;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.core.LocalVariableTableParameterNameDiscoverer;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.time.LocalDateTime;
import java.util.Arrays;

@Aspect
@Component
@Slf4j
@ConditionalOnProperty(prefix = "log", name = "controller")
public class LoggingController {

    @Pointcut("within(com.ws.hw1.controller.*.*Controller)")
    public void controllerPointCut() {
    }

    @AfterThrowing(pointcut = "controllerPointCut()", throwing = "e")
    public void logAfterThrowing(JoinPoint joinPoint, Throwable e) {
        log.error("Exception in {}.{}() with message = {}", joinPoint.getSignature().getDeclaringTypeName(),
                  joinPoint.getSignature().getName(), e.getMessage() != null ? e.getMessage() : "NULL");
    }

    @Around("controllerPointCut()")
    public Object around(ProceedingJoinPoint point) throws Throwable {
        Object result;

        try {
            result = point.proceed();
            saveLog(point);
        } catch (IllegalArgumentException e) {
            log.error("Illegal argument: {} in {}.{}()", Arrays.toString(point.getArgs()),
                      point.getSignature().getDeclaringTypeName(), point.getSignature().getName());
            throw e;
        }

        return result;
    }

    private void saveLog(ProceedingJoinPoint joinPoint) {
        StringBuilder logger = new StringBuilder();

        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();

        logger.append(logMethodName(joinPoint, signature));
        logger.append(logParams(joinPoint, method));

        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();

        logger.append(logServletRequest(request));

        log.info(logger.toString());
    }

    private StringBuilder logMethodName(ProceedingJoinPoint joinPoint, MethodSignature signature) {
        String className = joinPoint.getTarget().getClass().getName();
        String methodName = signature.getName();

        return new StringBuilder()
                .append("method= ")
                .append(className)
                .append(".")
                .append(methodName)
                .append("()");

    }

    private StringBuilder logParams(ProceedingJoinPoint joinPoint, Method method) {
        Object[] args = joinPoint.getArgs();
        LocalVariableTableParameterNameDiscoverer nameDiscoverer = new LocalVariableTableParameterNameDiscoverer();
        String[] paramNames = nameDiscoverer.getParameterNames(method);

        StringBuilder params = new StringBuilder();
        params.append("params=");

        if (args != null && paramNames != null) {
            for (int i = 0; i < args.length; i++) {
                params.append("  ")
                      .append(paramNames[i])
                      .append(": ")
                      .append(args[i]);
            }
        }

        return params;
    }

    private StringBuilder logServletRequest(HttpServletRequest request) {
        return new StringBuilder()
                .append("ipAddress= ")
                .append(request.getRemoteAddr())
                .append("endpoint= ")
                .append(request.getServletPath())
                .append("requestTime= ")
                .append(LocalDateTime.now())
                .append("operation= ")
                .append(request.getMethod());
    }
}

package com.ws.hw1.exceptionhandler;

import com.ws.hw1.exceptionhandler.exception.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ApiExceptionHandler {
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler
    public String handleNotFoundException(NotFoundException ex) {
        return ex.getMessage();
    }
}

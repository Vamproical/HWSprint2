package com.ws.hw1.exceptionhandler;

import com.ws.hw1.exceptionhandler.exception.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class ApiExceptionHandler {
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NotFoundException.class)
    public ErrorDto handleNotFoundException(NotFoundException ex) {
        return ErrorDto.builder()
                       .timestamp(LocalDateTime.now())
                       .message(ex.getMessage())
                       .build();
    }
}

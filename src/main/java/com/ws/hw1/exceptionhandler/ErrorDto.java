package com.ws.hw1.exceptionhandler;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Builder
@Data
public class ErrorDto {
    LocalDateTime timestamp;
    String message;
}

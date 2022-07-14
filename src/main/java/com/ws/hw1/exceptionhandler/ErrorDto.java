package com.ws.hw1.exceptionhandler;

import lombok.Builder;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;

import java.time.LocalDateTime;

@Builder
@Jacksonized
@Data
public class ErrorDto {
    LocalDateTime timestamp;
    String message;
}

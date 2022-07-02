package com.ws.hw1.exceptionhandler;

import lombok.Builder;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;

@Builder
@Jacksonized
@Data
public class ErrorDto {
    String message;
}

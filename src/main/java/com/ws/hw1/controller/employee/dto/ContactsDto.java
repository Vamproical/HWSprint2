package com.ws.hw1.controller.employee.dto;

import lombok.Builder;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;

import javax.validation.constraints.Pattern;

@Data
@Builder
@Jacksonized
public class ContactsDto {
    @Pattern(regexp = "(^$|\\d{10})")
    private String phone;
    @Pattern(regexp = "^(.+)@(\\S+)$")
    private String email;
    @Pattern(regexp = "^(.+)@(\\S+)$")
    private String workEmail;
}

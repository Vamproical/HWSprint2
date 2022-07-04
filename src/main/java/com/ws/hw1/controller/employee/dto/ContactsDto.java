package com.ws.hw1.controller.employee.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;

import javax.validation.constraints.Email;
import javax.validation.constraints.Pattern;

@Data
@AllArgsConstructor
@Builder
@Jacksonized
public class ContactsDto {
    @Pattern(regexp = "(^$|\\d{10})")
    private String phone;
    @Email
    private String email;
    @Email
    private String workEmail;
}

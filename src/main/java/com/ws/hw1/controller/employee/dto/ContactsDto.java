package com.ws.hw1.controller.employee.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.Email;
import javax.validation.constraints.Pattern;

@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class ContactsDto {
    @Pattern(regexp = "(^$|\\d{10})")
    private String phone;
    @Email
    private String email;
    @Email
    private String workEmail;
}

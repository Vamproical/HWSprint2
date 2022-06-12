package com.ws.hw1.controller.employee.dto;

import lombok.Data;

import javax.validation.constraints.Pattern;

@Data
public class ContactsDto {
    @Pattern(regexp = "(^$|\\d{10})")
    private String phone;
    @Pattern(regexp = "^(.+)@(\\S+)$")
    private String email;
    @Pattern(regexp = "^(.+)@(\\S+)$")
    private String workEmail;
}

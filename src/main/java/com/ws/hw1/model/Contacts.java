package com.ws.hw1.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class Contacts {
    private String phone;
    private String email;
    private String workEmail;
}

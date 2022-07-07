package com.ws.hw1.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.jackson.Jacksonized;

import javax.persistence.Embeddable;

@AllArgsConstructor
@Data
@Builder
@Jacksonized
@Embeddable
@NoArgsConstructor
public class Contacts {
    String phone;
    String email;
    String workEmail;
}

package com.ws.hw1.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;

@AllArgsConstructor
@Data
@Builder
@Embeddable
@NoArgsConstructor
public class Contacts {
    String phone;
    String email;
    String workEmail;

}

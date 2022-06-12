package com.ws.hw1.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@Data
@Builder
public class Employee {
    private UUID id;
    private String firstName;
    private String lastName;
    private String description;
    private List<String> characteristics;
    private Post post;
    private Contacts contacts;
    private JobType jobType;
}

package com.ws.hw1.model;

import lombok.*;

import java.util.List;

@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@ToString
@Builder
public class Employee {
    @NonNull
    private String firstName;
    @NonNull
    private String lastName;
    private String description;
    @NonNull
    private List<String> characteristics;
    @NonNull
    private Post post;
}

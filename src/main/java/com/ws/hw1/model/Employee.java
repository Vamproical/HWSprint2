package com.ws.hw1.model;

import lombok.*;
import org.jetbrains.annotations.NotNull;

import java.util.List;

@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@ToString
@Builder
public class Employee {
    @NotNull("First name may not be null")
    private String firstName;
    @NotNull("Last name may not be null")
    private String lastName;
    private String description;
    @NotNull("Characteristics may not be null")
    private List<String> characteristics;
    @NotNull("Post may not be null")
    private Post post;
}

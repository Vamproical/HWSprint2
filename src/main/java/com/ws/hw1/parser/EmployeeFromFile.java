package com.ws.hw1.parser;

import lombok.*;

import javax.validation.constraints.NotNull;
import java.util.List;

@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@ToString
@Builder
public class EmployeeFromFile {
    @NotNull
    private String firstName;
    @NotNull
    private String lastName;
    private String description;
    @NotNull
    private List<String> characteristics;
    @NotNull
    private String postId;
}

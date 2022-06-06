package com.ws.hw1.controller.dto.employee;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
public class CreateEmployeeDto {
    @NotBlank
    private String firstName;
    @NotBlank
    private String lastName;
    private String description;
    @NotNull
    private List<String> characteristics;
    @NotNull
    private UUID postId;
}

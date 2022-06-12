package com.ws.hw1.controller.employee.dto;

import com.ws.hw1.model.JobType;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
public class UpdateEmployeeDto {
    @NotBlank
    private String firstName;
    @NotBlank
    private String lastName;
    private String description;
    @NotNull
    private List<String> characteristics;
    @NotNull
    private UUID postId;
    @NotNull
    private ContactsDto contacts;
    @NotNull
    private JobType jobType;
}

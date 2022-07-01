package com.ws.hw1.controller.employee.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.UUID;

@Data
public class SearchParamsDto {
    @NotNull
    String name;
    @NotNull
    UUID postId;
}

package com.ws.hw1.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.UUID;

@AllArgsConstructor
@Data
public class Post {
    @NotNull
    private UUID id;
    @NotBlank
    private String name;
}

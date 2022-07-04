package com.ws.hw1.controller.employee.dto;

import lombok.Data;

import java.util.UUID;

@Data
public class SearchParamsDto {
    String name;
    UUID postId;
}

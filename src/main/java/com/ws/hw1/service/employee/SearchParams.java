package com.ws.hw1.service.employee;

import lombok.Value;

import java.util.UUID;

@Value
public class SearchParams {
    String name;
    UUID postId;
}

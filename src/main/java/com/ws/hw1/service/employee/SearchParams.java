package com.ws.hw1.service.employee;

import lombok.*;

import java.util.UUID;

@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@Builder
public class SearchParams {
    private String name;
    private UUID postId;
}

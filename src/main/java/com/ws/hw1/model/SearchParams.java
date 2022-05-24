package com.ws.hw1.model;

import lombok.*;

import java.util.UUID;

@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@Builder
public class SearchParams {
    private UUID id;
    private String firstName;
    private String lastName;
    private UUID postID;
}

package com.ws.hw1.model;

import lombok.*;

@NoArgsConstructor
@Setter
@Getter
@EqualsAndHashCode
public class ArgsModel {
    private String path;
    private SearchParams searchParams;
}

package com.ws.hw1.service.argument;

import com.ws.hw1.model.Post;
import lombok.Builder;
import lombok.Value;

import java.util.List;

@Value
@Builder
public class CreateEmployeeArgument {
    String firstName;
    String lastName;
    String description;
    List<String> characteristics;
    Post post;
}

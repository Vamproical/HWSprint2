package com.ws.hw1.service.argument;

import com.ws.hw1.model.Contacts;
import com.ws.hw1.model.JobType;
import com.ws.hw1.model.Post;
import lombok.Builder;
import lombok.Value;

import java.util.List;

@Value
@Builder
public class UpdateEmployeeArgument {
    String firstName;
    String lastName;
    String description;
    List<String> characteristics;
    Post post;
    Contacts contacts;
    JobType jobType;
}

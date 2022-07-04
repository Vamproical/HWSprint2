package com.ws.hw1.service.argument;

import com.ws.hw1.model.Contacts;
import com.ws.hw1.model.JobType;
import com.ws.hw1.model.Post;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreateEmployeeArgument {
    String firstName;
    String lastName;
    String description;
    List<String> characteristics;
    Post post;
    Contacts contacts;
    JobType jobType;
}

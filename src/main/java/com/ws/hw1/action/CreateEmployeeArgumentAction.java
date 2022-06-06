package com.ws.hw1.action;

import com.ws.hw1.controller.dto.employee.CreateEmployeeDto;
import com.ws.hw1.service.argument.CreateEmployeeArgument;
import com.ws.hw1.service.post.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CreateEmployeeArgumentAction {
    private final PostService postService;

    public CreateEmployeeArgument execute(CreateEmployeeDto createEmployeeDto) {
        return CreateEmployeeArgument.builder()
                                     .firstName(createEmployeeDto.getFirstName())
                                     .lastName(createEmployeeDto.getLastName())
                                     .description(createEmployeeDto.getDescription())
                                     .characteristics(createEmployeeDto.getCharacteristics())
                                     .post(postService.get(createEmployeeDto.getPostId()))
                                     .build();
    }
}

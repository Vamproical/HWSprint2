package com.ws.hw1.action;

import com.ws.hw1.controller.employee.dto.UpdateEmployeeDto;
import com.ws.hw1.controller.employee.mapper.EmployeeMapper;
import com.ws.hw1.service.argument.CreateEmployeeArgument;
import com.ws.hw1.service.post.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UpdateEmployeeArgumentAction {
    private final PostService postService;
    private final EmployeeMapper employeeMapper;

    public CreateEmployeeArgument execute(UpdateEmployeeDto createEmployeeDto) {
        return CreateEmployeeArgument.builder()
                                     .firstName(createEmployeeDto.getFirstName())
                                     .lastName(createEmployeeDto.getLastName())
                                     .description(createEmployeeDto.getDescription())
                                     .characteristics(createEmployeeDto.getCharacteristics())
                                     .post(postService.get(createEmployeeDto.getPostId()))
                                     .contacts(
                                             employeeMapper.toModel(createEmployeeDto.getContacts())
                                     )
                                     .jobType(createEmployeeDto.getJobType())
                                     .build();
    }
}

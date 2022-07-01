package com.ws.hw1.action;

import com.ws.hw1.controller.employee.dto.CreateEmployeeDto;
import com.ws.hw1.controller.employee.mapper.EmployeeMapper;
import com.ws.hw1.model.Post;
import com.ws.hw1.service.argument.CreateEmployeeArgument;
import com.ws.hw1.service.post.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CreateEmployeeArgumentAction {
    private final PostService postService;
    private final EmployeeMapper employeeMapper;

    public CreateEmployeeArgument execute(CreateEmployeeDto createEmployeeDto) {
        Post post = postService.get(createEmployeeDto.getPostId());
        return employeeMapper.toArgument(createEmployeeDto, post);
        //employeeMapper.toModel(createEmployeeDto.getContacts()
    }
}

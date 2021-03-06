package com.ws.hw1.action;

import com.ws.hw1.controller.employee.dto.UpdateEmployeeDto;
import com.ws.hw1.controller.employee.mapper.EmployeeMapper;
import com.ws.hw1.model.Post;
import com.ws.hw1.service.argument.UpdateEmployeeArgument;
import com.ws.hw1.service.post.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UpdateEmployeeArgumentAction {
    private final PostService postService;
    private final EmployeeMapper employeeMapper;

    public UpdateEmployeeArgument execute(UpdateEmployeeDto updateEmployeeDto) {
        Post post = postService.getExisting(updateEmployeeDto.getPostId());
        return employeeMapper.toUpdateArgument(updateEmployeeDto, post);
    }
}

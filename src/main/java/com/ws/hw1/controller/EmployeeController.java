package com.ws.hw1.controller;

import com.ws.hw1.action.CreateEmployeeArgumentAction;
import com.ws.hw1.controller.dto.employee.CreateEmployeeDto;
import com.ws.hw1.controller.dto.employee.EmployeeDto;
import com.ws.hw1.mapper.EmployeeMapper;
import com.ws.hw1.model.Employee;
import com.ws.hw1.service.employee.EmployeeService;
import com.ws.hw1.service.employee.SearchParams;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/employee")
@AllArgsConstructor
public class EmployeeController {
    private final EmployeeService employeeService;
    private final EmployeeMapper employeeMapper;
    private final CreateEmployeeArgumentAction createEmployeeArgumentAction;

    @ApiOperation("Добавить нового сотрудника")
    @PostMapping("create")
    @ResponseStatus(code = HttpStatus.CREATED)
    public EmployeeDto addNewEmployee(@RequestBody CreateEmployeeDto employeeDto) {
        Employee newEmployee = employeeService.create(createEmployeeArgumentAction.execute(employeeDto));
        return employeeMapper.toDTO(newEmployee);
    }

    @ApiOperation("Обновить характеристики сотрудника")
    @PutMapping("{id}")
    public EmployeeDto updateEmployee(@PathVariable UUID id, @RequestBody @Valid CreateEmployeeDto employeeDto) {
        Employee updatedPost = employeeService.update(id, createEmployeeArgumentAction.execute(employeeDto));
        return employeeMapper.toDTO(updatedPost);
    }

    @ApiOperation("Удалить сотрудника")
    @DeleteMapping("{id}")
    public void deletePost(@PathVariable UUID id) {
        employeeService.delete(id);
    }

    @ApiOperation("Получить сотрудника по индентификатору")
    @GetMapping("{id}")
    public EmployeeDto getById(@PathVariable UUID id) {
        return employeeMapper.toDTO(employeeService.get(id));
    }

    @ApiOperation("Получить список сотрудников")
    @GetMapping
    public List<EmployeeDto> getAll(@RequestParam(required = false) String name, @RequestParam(required = false) UUID postId) {
        return employeeService.getAll(new SearchParams(name, postId))
                              .stream()
                              .map(employeeMapper::toDTO)
                              .collect(Collectors.toList());
    }

}

package com.ws.hw1.controller.employee;

import com.ws.hw1.action.CreateEmployeeArgumentAction;
import com.ws.hw1.action.UpdateEmployeeArgumentAction;
import com.ws.hw1.controller.employee.dto.CreateEmployeeDto;
import com.ws.hw1.controller.employee.dto.EmployeeDto;
import com.ws.hw1.controller.employee.dto.SearchParamsDto;
import com.ws.hw1.controller.employee.dto.UpdateEmployeeDto;
import com.ws.hw1.controller.employee.mapper.EmployeeMapper;
import com.ws.hw1.exceptionhandler.exception.NotFoundException;
import com.ws.hw1.model.Employee;
import com.ws.hw1.service.argument.CreateEmployeeArgument;
import com.ws.hw1.service.argument.UpdateEmployeeArgument;
import com.ws.hw1.service.employee.EmployeeService;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.SortDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("employee")
@AllArgsConstructor
public class EmployeeController {
    private final EmployeeService employeeService;
    private final EmployeeMapper employeeMapper;
    private final CreateEmployeeArgumentAction createEmployeeArgumentAction;
    private final UpdateEmployeeArgumentAction updateEmployeeArgumentAction;

    @ApiOperation("Добавить нового сотрудника")
    @PostMapping("create")
    @ResponseStatus(code = HttpStatus.CREATED)
    public EmployeeDto create(@RequestBody @Valid CreateEmployeeDto employeeDto) {
        CreateEmployeeArgument employeeArgument = createEmployeeArgumentAction.execute(employeeDto);
        Employee newEmployee = employeeService.create(employeeArgument);
        return employeeMapper.toDTO(newEmployee);
    }

    @ApiOperation("Обновить характеристики сотрудника")
    @PutMapping("{id}/update")
    public EmployeeDto update(@PathVariable UUID id, @RequestBody @Valid UpdateEmployeeDto employeeDto) throws NotFoundException {
        UpdateEmployeeArgument employeeArgument = updateEmployeeArgumentAction.execute(employeeDto);
        Employee updatedPost = employeeService.update(id, employeeArgument);
        return employeeMapper.toDTO(updatedPost);
    }

    @ApiOperation("Удалить сотрудника")
    @DeleteMapping("{id}/delete")
    public void delete(@PathVariable UUID id) throws NotFoundException {
        employeeService.delete(id);
    }

    @ApiOperation("Получить сотрудника по индентификатору")
    @GetMapping("{id}")
    public EmployeeDto getById(@PathVariable UUID id) throws NotFoundException {
        return employeeMapper.toDTO(employeeService.getExisting(id));
    }

    @ApiOperation("Получить список сотрудников")
    @GetMapping("list")
    public List<EmployeeDto> getAll(SearchParamsDto searchParamsDto,
                                    @SortDefault.SortDefaults({
                                            @SortDefault(sort = "lastName", direction = Sort.Direction.ASC),
                                            @SortDefault(sort = "firstName", direction = Sort.Direction.DESC)
                                    }) Sort sort) {
        return employeeService.getAll(employeeMapper.toParams(searchParamsDto), sort)
                              .stream()
                              .map(employeeMapper::toDTO)
                              .collect(Collectors.toList());
    }

}

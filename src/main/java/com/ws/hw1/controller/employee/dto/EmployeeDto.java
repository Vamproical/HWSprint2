package com.ws.hw1.controller.employee.dto;

import com.ws.hw1.model.Contacts;
import com.ws.hw1.model.JobType;
import com.ws.hw1.model.Post;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
@ApiModel("Модель сотрудник")
public class EmployeeDto {
    private UUID id;
    @ApiModelProperty("Имя")
    private String firstName;
    @ApiModelProperty("Фамилия")
    private String lastName;
    @ApiModelProperty("Описание сотрудника")
    private String description;
    @ApiModelProperty("Характеристики сотрудника")
    private List<String> characteristics;
    @ApiModelProperty("Должность сотрудника")
    private Post post;
    @ApiModelProperty("Контактные данные сотрудника")
    private Contacts contacts;
    @ApiModelProperty("Тип работы")
    private JobType jobType;

}

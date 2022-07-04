package com.ws.hw1.controller.employee.dto;

import com.ws.hw1.controller.post.dto.PostDto;
import com.ws.hw1.model.JobType;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
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
    private PostDto post;
    @ApiModelProperty("Контактные данные сотрудника")
    private ContactsDto contacts;
    @ApiModelProperty("Тип работы")
    private JobType jobType;

}

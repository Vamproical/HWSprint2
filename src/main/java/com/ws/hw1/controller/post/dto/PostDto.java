package com.ws.hw1.controller.post.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel("Модель должности")
public class PostDto {

    @NotBlank
    private UUID id;

    @NotNull
    @ApiModelProperty("Название должности")
    private String name;
}

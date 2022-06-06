package com.ws.hw1.controller;

import com.ws.hw1.controller.dto.post.CreatePostDto;
import com.ws.hw1.controller.dto.post.PostDto;
import com.ws.hw1.exceptionHandler.exception.NotFoundException;
import com.ws.hw1.mapper.PostMapper;
import com.ws.hw1.model.Post;
import com.ws.hw1.service.argument.CreatePostArgument;
import com.ws.hw1.service.post.PostService;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/post")
@AllArgsConstructor
public class PostController {
    private final PostService postService;
    private final PostMapper postMapper;

    @ApiOperation("Добавить должность")
    @PostMapping("create")
    @ResponseStatus(code = HttpStatus.CREATED)
    public PostDto addNewPost(@RequestBody CreatePostDto postDto) {
        Post newPost = postService.create(CreatePostArgument.builder()
                                                            .name(postDto.getName())
                                                            .build());
        return postMapper.toDTO(newPost);
    }

    @ApiOperation("Обновить должность")
    @PutMapping("{id}")
    public PostDto updatePost(@PathVariable UUID id, @RequestBody CreatePostDto postDto) throws NotFoundException {
        Post updatedPost = postService.update(id, CreatePostArgument.builder()
                                                                    .name(postDto.getName())
                                                                    .build());
        return postMapper.toDTO(updatedPost);
    }

    @ApiOperation("Удалить должность")
    @DeleteMapping("{id}")
    public void deletePost(@PathVariable UUID id) throws NotFoundException {
        postService.delete(id);
    }

    @ApiOperation("Получить должность по индентификатору")
    @GetMapping("{id}")
    public PostDto getById(@PathVariable UUID id) throws NotFoundException {
        return postMapper.toDTO(postService.get(id));
    }

    @ApiOperation("Получить список должностей")
    @GetMapping
    public List<PostDto> getAll() {
        return postService.getAll()
                          .stream()
                          .map(postMapper::toDTO)
                          .collect(Collectors.toList());
    }

}

package com.ws.hw1.controller.post;

import com.ws.hw1.controller.post.dto.CreatePostDto;
import com.ws.hw1.controller.post.dto.PostDto;
import com.ws.hw1.controller.post.dto.UpdatePostDto;
import com.ws.hw1.controller.post.mapper.PostMapper;
import com.ws.hw1.exceptionhandler.exception.NotFoundException;
import com.ws.hw1.model.Post;
import com.ws.hw1.service.argument.CreatePostArgument;
import com.ws.hw1.service.post.PostService;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("post")
@AllArgsConstructor
public class PostController {
    private final PostService postService;
    private final PostMapper postMapper;

    @ApiOperation("Добавить должность")
    @PostMapping("create")
    @ResponseStatus(code = HttpStatus.CREATED)
    public PostDto addNewPost(@RequestBody @Valid CreatePostDto postDto) {
        CreatePostArgument postArgument = CreatePostArgument.builder()
                                                            .name(postDto.getName())
                                                            .build();
        Post newPost = postService.create(postArgument);
        return postMapper.toDTO(newPost);
    }

    @ApiOperation("Обновить должность")
    @PutMapping("{id}/update")
    public PostDto updatePost(@PathVariable UUID id, @RequestBody @Valid UpdatePostDto postDto) throws NotFoundException {
        Post updatedPost = postService.update(id, postDto);
        return postMapper.toDTO(updatedPost);
    }

    @ApiOperation("Удалить должность")
    @DeleteMapping("{id}/delete")
    public void deletePost(@PathVariable UUID id) throws NotFoundException {
        postService.delete(id);
    }

    @ApiOperation("Получить должность по индентификатору")
    @GetMapping("{id}")
    public PostDto getById(@PathVariable UUID id) throws NotFoundException {
        return postMapper.toDTO(postService.get(id));
    }

    @ApiOperation("Получить список должностей")
    @GetMapping("list")
    public List<PostDto> getAll() {
        return postService.getAll()
                          .stream()
                          .map(postMapper::toDTO)
                          .collect(Collectors.toList());
    }

}

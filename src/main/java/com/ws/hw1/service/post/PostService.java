package com.ws.hw1.service.post;

import com.ws.hw1.controller.post.dto.UpdatePostDto;
import com.ws.hw1.model.Post;
import com.ws.hw1.service.argument.CreatePostArgument;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.UUID;

public interface PostService {
    Post get(@NotNull UUID postId);

    Post create(@NotNull CreatePostArgument argumentPost);

    Post update(@NotNull UUID id, @NotNull UpdatePostDto argumentPost);

    void delete(@NotNull UUID id);

    List<Post> getAll();
}

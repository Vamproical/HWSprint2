package com.ws.hw1.service.post;

import com.ws.hw1.controller.post.dto.UpdatePostDto;
import com.ws.hw1.model.Post;
import com.ws.hw1.service.argument.CreatePostArgument;
import lombok.NonNull;

import java.util.List;
import java.util.UUID;

public interface PostService {
    Post getExisting(@NonNull UUID postId);

    Post create(@NonNull CreatePostArgument argumentPost);

    Post update(@NonNull UUID id, @NonNull UpdatePostDto argumentPost);

    void delete(@NonNull UUID id);

    List<Post> getAll();
}

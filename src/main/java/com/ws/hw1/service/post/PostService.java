package com.ws.hw1.service.post;

import com.ws.hw1.model.Post;
import com.ws.hw1.service.argument.CreatePostArgument;

import java.util.List;
import java.util.UUID;

public interface PostService {
    Post get(UUID postId);

    Post create(CreatePostArgument argumentPost);

    Post update(UUID id, CreatePostArgument argumentPost);

    void delete(UUID id);

    List<Post> getAll();
}

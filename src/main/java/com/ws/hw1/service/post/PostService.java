package com.ws.hw1.service.post;

import com.ws.hw1.controller.post.dto.UpdatePostDto;
import com.ws.hw1.model.Post;
import com.ws.hw1.service.argument.CreatePostArgument;
import lombok.NonNull;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

public interface PostService {
    @Transactional
    Post getExisting(@NonNull UUID postId);

    @Transactional
    Post create(@NonNull CreatePostArgument argumentPost);

    @Transactional
    Post update(@NonNull UUID id, @NonNull UpdatePostDto argumentPost);

    @Transactional
    void delete(@NonNull UUID id);

    @Transactional
    List<Post> getAll();
}

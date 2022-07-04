package com.ws.hw1.service.post;

import com.ws.hw1.controller.post.dto.UpdatePostDto;
import com.ws.hw1.model.Post;
import com.ws.hw1.service.argument.CreatePostArgument;
import com.ws.hw1.utils.Guard;
import lombok.NonNull;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class PostServiceImpl implements PostService {
    private final Map<UUID, Post> posts = new HashMap<>();

    @Override
    public Post getExisting(@NonNull UUID id) {
        Guard.check(posts.containsKey(id), "The post not found");
        return posts.get(id);
    }

    @Override
    public Post create(@NonNull CreatePostArgument argumentPost) {
        UUID id = UUID.randomUUID();
        Post post = Post.builder()
                        .id(id)
                        .name(argumentPost.getName()).build();

        posts.put(id, post);

        return post;
    }

    @Override
    public Post update(@NonNull UUID id, @NonNull UpdatePostDto argumentPost) {
        Guard.check(posts.containsKey(id), "The post not found");

        Post post = getExisting(id);
        post.setName(argumentPost.getName());

        return post;
    }

    @Override
    public void delete(@NonNull UUID id) {
        Guard.check(posts.containsKey(id), "The post not found");
        posts.remove(id);
    }

    @Override
    public List<Post> getAll() {
        return new ArrayList<>(posts.values());
    }

}

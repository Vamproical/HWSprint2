package com.ws.hw1.service.post;

import com.ws.hw1.exceptionHandler.exception.NotFoundException;
import com.ws.hw1.model.Post;
import com.ws.hw1.service.argument.CreatePostArgument;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class PostServiceImpl implements PostService {
    private final Map<UUID, Post> posts = new HashMap<>();

    @Override
    public Post get(UUID id) {
        throwExceptionIfNotExits(id);
        return posts.get(id);
    }

    @Override
    public Post create(CreatePostArgument argumentPost) {
        UUID id = UUID.randomUUID();
        Post post = createPost(id, argumentPost);
        posts.put(id, post);
        return post;
    }

    @Override
    public Post update(UUID id, CreatePostArgument argumentPost) {
        throwExceptionIfNotExits(id);
        Post post = createPost(id, argumentPost);
        posts.replace(id, post);
        return post;
    }

    @Override
    public void delete(UUID id) {
        throwExceptionIfNotExits(id);
        posts.remove(id);
    }

    @Override
    public List<Post> getAll() {
        return new ArrayList<>(posts.values());
    }

    private void throwExceptionIfNotExits(UUID id) {
        if (!posts.containsKey(id)) {
            throw new NotFoundException();
        }
    }

    private Post createPost(UUID id, CreatePostArgument postArgument) {
        return new Post(id,
                postArgument.getName());
    }
}

package com.ws.hw1.service;

import com.ws.hw1.model.Post;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class PostServiceImpl implements PostService {
    private final Map<UUID, Post> posts = new HashMap<>();

    public PostServiceImpl() {
        posts.put(UUID.fromString("854ef89d-6c27-4635-926d-894d76a81707"),
                new Post(UUID.fromString("854ef89d-6c27-4635-926d-894d76a81707"), "Tech lead"));
        posts.put(UUID.fromString("762d15a5-3bc9-43ef-ae96-02a680a557d0"),
                new Post(UUID.fromString("762d15a5-3bc9-43ef-ae96-02a680a557d0"),"Tech Writer"));
    }

    @Override
    public Post getPost(String postId) {
        return posts.get(UUID.fromString(postId));
    }
}

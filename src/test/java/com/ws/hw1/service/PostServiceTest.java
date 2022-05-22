package com.ws.hw1.service;

import com.ws.hw1.model.Post;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class PostServiceTest {
    private final PostService postService = new PostService();

    @Test
    void convertTest() {
        Post post = new Post(UUID.fromString("854ef89d-6c27-4635-926d-894d76a81707"), "Tech lead");
        Assertions.assertEquals(post, postService.convert(post.getId().toString()));
    }

    @Test
    void convertTestWithNull() {
        Post post = new Post(UUID.randomUUID(), "Tech lead");
        Assertions.assertNull(postService.convert(post.getId().toString()));
    }
}
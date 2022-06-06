package com.ws.hw1.service;

import com.ws.hw1.model.Post;
import com.ws.hw1.service.post.PostService;
import com.ws.hw1.service.post.PostServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.UUID;

class PostServiceTest {
    private final PostService postService = new PostServiceImpl();

    @Test
    void convertTest() {
        Post excepted = new Post(UUID.fromString("854ef89d-6c27-4635-926d-894d76a81707"), "Tech lead");
        Post actual = postService.get(excepted.getId());
        Assertions.assertEquals(excepted, actual);
    }

    @Test
    void convertTestWithNull() {
        Post excepted = new Post(UUID.randomUUID(), "Tech lead");
        Post actual = postService.get(excepted.getId());
        Assertions.assertNull(actual);
    }
}
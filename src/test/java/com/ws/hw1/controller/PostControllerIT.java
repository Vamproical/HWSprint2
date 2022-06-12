package com.ws.hw1.controller;

import com.ws.hw1.controller.post.dto.CreatePostDto;
import com.ws.hw1.controller.post.dto.PostDto;
import com.ws.hw1.service.argument.CreatePostArgument;
import com.ws.hw1.service.post.PostService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.WebTestClient;

@AutoConfigureWebTestClient
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class PostControllerIT {
    private final CreatePostDto createDto = new CreatePostDto("Tech Writer");
    @Autowired
    private WebTestClient webTestClient;
    @Autowired
    private PostService postService;

    @Test
    void post() {
        //arrange
        PostDto postDto = webTestClient.post()
                                       .uri(uriBuilder -> uriBuilder.path("/api/post/create")
                                                                    .build())
                                       .bodyValue(createDto)
                                       .exchange()
                                       //act
                                       .expectStatus()
                                       .isCreated()
                                       .expectBody(PostDto.class)
                                       .returnResult()
                                       .getResponseBody();
        //assert
        Assertions.assertEquals(createDto.getName(), postDto.getName());
    }

    @Test
    void get() {
        //arrange
        postService.create(CreatePostArgument.builder()
                                             .name(createDto.getName())
                                             .build());
        PostDto postDto = (PostDto) webTestClient.get()
                                                 .uri(uriBuilder -> uriBuilder.path("api/post/{id}")
                                                                              .build());
    }
}

package com.ws.hw1.controller;

import com.ws.hw1.controller.post.dto.CreatePostDto;
import com.ws.hw1.controller.post.dto.PostDto;
import com.ws.hw1.controller.post.dto.UpdatePostDto;
import com.ws.hw1.exceptionhandler.ErrorDto;
import com.ws.hw1.model.Post;
import com.ws.hw1.service.argument.CreatePostArgument;
import com.ws.hw1.service.post.PostService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@AutoConfigureWebTestClient
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class PostControllerIT {
    private final CreatePostDto createDto = new CreatePostDto("Tech Writer");
    private final UpdatePostDto updateDto = new UpdatePostDto("Lead Developer");
    private final CreatePostArgument postArgument = CreatePostArgument.builder()
                                                                      .name("Tech Writer")
                                                                      .build();
    @Autowired
    private WebTestClient webTestClient;
    @Autowired
    private PostService postService;

    @BeforeEach
    void setUpPost() {
        postService.create(postArgument);
    }

    private Post getFirst() {
        return postService.getAll().get(0);
    }

    @Test
    void create() {
        //Arrange
        UUID id = UUID.randomUUID();
        PostDto expected = new PostDto(id, "Tech Writer");

        //Act
        PostDto actual = webTestClient.post()
                                      .uri(uriBuilder -> uriBuilder.path("post/create")
                                                                   .build())
                                      .bodyValue(createDto)
                                      .exchange()
                                      //Assert
                                      .expectStatus()
                                      .isCreated()
                                      .expectBody(PostDto.class)
                                      .returnResult()
                                      .getResponseBody();

        Assertions.assertNotNull(actual.getId());
        assertThat(actual).usingRecursiveComparison().ignoringFields("id").isEqualTo(expected);
    }

    @Test
    void get() {
        //Arrange
        Post post1 = getFirst();
        UUID getId = post1.getId();
        PostDto expected = new PostDto(getId, "Tech Writer");

        //Act
        PostDto actual = webTestClient.get()
                                      .uri("post/{id}", getId)
                                      .exchange()
                                      //Arrange
                                      .expectStatus()
                                      .isOk()
                                      .expectBody(PostDto.class)
                                      .returnResult()
                                      .getResponseBody();

        Assertions.assertEquals(expected, actual);
    }

    @Test
    void tryGetAndGetNotFound() {
        //Arrange
        UUID id = UUID.randomUUID();
        ErrorDto errorDto = ErrorDto.builder()
                                    .message("The post not found")
                                    .build();

        //Act
        webTestClient.get()
                     .uri("post/{id}", id)
                     .exchange()
                     //Arrange
                     .expectStatus()
                     .isNotFound()
                     .expectBody(ErrorDto.class)
                     .isEqualTo(errorDto);
    }

    @Test
    void update() {
        //Assert
        Post post = getFirst();
        UUID id = post.getId();
        PostDto expected = new PostDto(id, "Lead Developer");

        //Act
        PostDto actual = webTestClient.put()
                                      .uri("post/{id}/update", id)
                                      .bodyValue(updateDto)
                                      .exchange()
                                      //Arrange
                                      .expectStatus()
                                      .isOk()
                                      .expectBody(PostDto.class)
                                      .returnResult()
                                      .getResponseBody();

        Assertions.assertEquals(expected, actual);
    }

    @Test
    void tryUpdateAndGetNotFound() {
        //Assert
        UUID id = UUID.randomUUID();
        ErrorDto errorDto = ErrorDto.builder()
                                    .message("The post not found")
                                    .build();

        //Act
        webTestClient.put()
                     .uri("post/{id}/update", id)
                     .bodyValue(updateDto)
                     .exchange()
                     //Arrange
                     .expectStatus()
                     .isNotFound()
                     .expectBody(ErrorDto.class)
                     .isEqualTo(errorDto);
    }
}

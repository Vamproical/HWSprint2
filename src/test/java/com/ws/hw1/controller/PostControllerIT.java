package com.ws.hw1.controller;

import com.ws.hw1.controller.post.dto.CreatePostDto;
import com.ws.hw1.controller.post.dto.PostDto;
import com.ws.hw1.controller.post.dto.UpdatePostDto;
import com.ws.hw1.exceptionhandler.exception.NotFoundException;
import com.ws.hw1.model.Post;
import com.ws.hw1.service.argument.CreatePostArgument;
import com.ws.hw1.service.post.PostService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@AutoConfigureWebTestClient
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class PostControllerIT {
    private final CreatePostDto createDto = new CreatePostDto("Tech Writer");
    private final UpdatePostDto updateDto = new UpdatePostDto("Lead Developer");
    private final CreatePostArgument postArgument = CreatePostArgument.builder()
                                                                      .name("Tech Writer")
                                                                      .build();
    private final UUID id = UUID.fromString("720eb7c5-b0b1-4a33-ae68-4d0d5feab2d6");
    private final Post post = new Post(id, "Tech Writer");
    private final Post updatedPost = new Post(id, "Lead Developer");
    @Autowired
    private WebTestClient webTestClient;
    @SpyBean
    private PostService postService;

    @Test
    void create() {
        //Arrange
        when(postService.create(postArgument)).thenReturn(post);

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

        PostDto expected = new PostDto(id, "Tech Writer");

        verify(postService).create(postArgument);
        Assertions.assertEquals(expected,actual);
    }

    @Test
    void get() {
        //Arrange
        doReturn(post).when(postService).get(any());

        //Act
        PostDto actual = webTestClient.get()
                                       .uri("post/{id}", id)
                                       .exchange()
                                       //Arrange
                                       .expectStatus()
                                       .isOk()
                                       .expectBody(PostDto.class)
                                       .returnResult()
                                       .getResponseBody();

        PostDto expected = new PostDto(id, "Tech Writer");

        verify(postService).get(id);
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void tryGetAndGetNotFound() {
        //Arrange
        UUID id = UUID.randomUUID();
        doThrow(NotFoundException.class).when(postService).get(id);

        //Act
        webTestClient.get()
                     .uri("post/{id}", id)
                     .exchange()
                     //Arrange
                     .expectStatus()
                     .isNotFound()
                     .expectBody(String.class)
                     .isEqualTo("The post id not found");
    }

    @Test
    void update() {
        //Assert
        doReturn(updatedPost).when(postService).update(any(), any());

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

        PostDto expected = new PostDto(id, "Lead Developer");

        verify(postService).update(id, updateDto);
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void tryUpdateAndGetNotFound() {
        //Assert
        doThrow(NotFoundException.class).when(postService).update(any(), any());

        //Act
        webTestClient.put()
                     .uri("post/{id}/update", id)
                     .bodyValue(updateDto)
                     .exchange()
                     //Arrange
                     .expectStatus()
                     .isNotFound()
                     .expectBody(String.class)
                     .isEqualTo("The post id is not found");
    }
}

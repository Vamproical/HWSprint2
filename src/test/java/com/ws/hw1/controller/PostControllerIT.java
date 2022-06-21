package com.ws.hw1.controller;

import com.ws.hw1.controller.post.dto.CreatePostDto;
import com.ws.hw1.controller.post.dto.PostDto;
import com.ws.hw1.controller.post.dto.UpdatePostDto;
import com.ws.hw1.exceptionHandler.exception.NotFoundException;
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
    void post() {
        //arrange
        when(postService.create(postArgument)).thenReturn(post);

        //act
        PostDto postDto = webTestClient.post()
                                       .uri(uriBuilder -> uriBuilder.path("post/create")
                                                                    .build())
                                       .bodyValue(createDto)
                                       .exchange()
                                       //assert
                                       .expectStatus()
                                       .isCreated()
                                       .expectBody(PostDto.class)
                                       .returnResult()
                                       .getResponseBody();

        verify(postService).create(postArgument);
        Assertions.assertEquals(post.getName(), postDto.getName());
    }

    @Test
    void get() {
        //arrange
        doReturn(post).when(postService).get(any());

        //act
        PostDto postDto = webTestClient.get()
                                       .uri(uriBuilder -> uriBuilder.path("post/" + id)
                                                                    .build())
                                       .exchange()
                                       //arrange
                                       .expectStatus()
                                       .isOk()
                                       .expectBody(PostDto.class)
                                       .returnResult()
                                       .getResponseBody();

        verify(postService).get(id);
        Assertions.assertEquals(post.getId(), postDto.getId());
        Assertions.assertEquals(post.getName(), postDto.getName());
    }

    @Test
    void tryGetAndGetNotFound() {
        //arrange
        UUID id = UUID.randomUUID();
        doThrow(NotFoundException.class).when(postService).get(id);

        //act
        webTestClient.get()
                     .uri(uriBuilder -> uriBuilder.path("post/" + id)
                                                  .build())
                     .exchange()
                     //arrange
                     .expectStatus()
                     .isNotFound()
                     .expectBody(String.class)
                     .isEqualTo("The specified id is not found");
    }

    @Test
    void update() {
        //assert
        doReturn(updatedPost).when(postService).update(any(), any());

        //act
        PostDto postDto = webTestClient.put()
                                       .uri(uriBuilder -> uriBuilder.path("post/" + id + "/update")
                                                                    .build())
                                       .bodyValue(updateDto)
                                       .exchange()
                                       //arrange
                                       .expectStatus()
                                       .isOk()
                                       .expectBody(PostDto.class)
                                       .returnResult()
                                       .getResponseBody();

        verify(postService).update(id, updateDto);
        Assertions.assertEquals(updatedPost.getId(), postDto.getId());
        Assertions.assertEquals(updatedPost.getName(), postDto.getName());
    }

    @Test
    void tryUpdateAndGetNotFound() {
        //assert
        doThrow(NotFoundException.class).when(postService).update(any(), any());

        //act
        webTestClient.put()
                     .uri(uriBuilder -> uriBuilder.path("post/" + id + "/update")
                                                  .build())
                     .bodyValue(updateDto)
                     .exchange()
                     //arrange
                     .expectStatus()
                     .isNotFound()
                     .expectBody(String.class)
                     .isEqualTo("The specified id is not found");
    }
}

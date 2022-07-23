package com.ws.hw1.controller;

import com.github.database.rider.core.api.dataset.DataSet;
import com.github.database.rider.core.api.dataset.ExpectedDataSet;
import com.jupiter.tools.spring.test.postgres.annotation.meta.EnablePostgresIntegrationTest;
import com.ws.hw1.controller.post.dto.CreatePostDto;
import com.ws.hw1.controller.post.dto.PostDto;
import com.ws.hw1.controller.post.dto.UpdatePostDto;
import com.ws.hw1.exceptionhandler.ErrorDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@AutoConfigureWebTestClient
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@EnablePostgresIntegrationTest
class PostControllerIT {
    private final UpdatePostDto updateDto = new UpdatePostDto("Senior Developer");
    @Autowired
    private WebTestClient webTestClient;


    @Test
    @DataSet(cleanBefore = true, cleanAfter = true, value = "datasets/post/create/create.json")
    @ExpectedDataSet(value = "datasets/post/create/expected_create.json")
    void create() {
        //Arrange
        CreatePostDto createDto = new CreatePostDto("Lead Developer");

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

        PostDto expected = new PostDto(UUID.randomUUID(), "Lead Developer");

        Assertions.assertNotNull(actual.getId());
        assertThat(actual).usingRecursiveComparison()
                          .ignoringFields("id")
                          .isEqualTo(expected);
    }

    @Test
    @DataSet(cleanBefore = true, cleanAfter = true, value = "datasets/post/get/get.json")
    void get() {
        //Arrange
        UUID id = UUID.fromString("ebc19c4f-5bd8-4165-813f-2ac699134ad4");

        //Act
        PostDto actual = webTestClient.get()
                                      .uri("post/{id}", id)
                                      .exchange()
                                      //Assert
                                      .expectStatus()
                                      .isOk()
                                      .expectBody(PostDto.class)
                                      .returnResult()
                                      .getResponseBody();

        PostDto expected = new PostDto(id, "Lead Developer");

        Assertions.assertEquals(expected, actual);
    }

    @Test
    @DataSet(cleanBefore = true, cleanAfter = true, value = "datasets/post/update/update.json")
    @ExpectedDataSet(value = "datasets/post/update/expected_update.json")
    void update() {
        //Arrange
        UUID id = UUID.fromString("ebc19c4f-5bd8-4165-813f-2ac699134ad4");

        //Act
        PostDto actual = webTestClient.put()
                                      .uri("post/{id}/update", id)
                                      .bodyValue(updateDto)
                                      .exchange()
                                      //Assert
                                      .expectStatus()
                                      .isOk()
                                      .expectBody(PostDto.class)
                                      .returnResult()
                                      .getResponseBody();

        PostDto expected = new PostDto(id, "Senior Developer");

        Assertions.assertEquals(expected, actual);
    }

    @Test
    @DataSet(cleanBefore = true, cleanAfter = true, value = "datasets/post/delete/delete.json")
    @ExpectedDataSet(value = "datasets/post/delete/expected_delete.json")
    void delete() {
        //Arrange
        UUID id = UUID.fromString("ebc19c4f-5bd8-4165-813f-2ac699134ad4");

        //Act
        webTestClient.delete()
                     .uri("post/{id}/delete", id)
                     .exchange()
                     //Assert
                     .expectStatus()
                     .isOk();
    }
}

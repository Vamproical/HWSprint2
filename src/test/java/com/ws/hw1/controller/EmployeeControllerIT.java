package com.ws.hw1.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ws.hw1.controller.employee.dto.ContactsDto;
import com.ws.hw1.controller.employee.dto.CreateEmployeeDto;
import com.ws.hw1.controller.employee.dto.EmployeeDto;
import com.ws.hw1.controller.employee.dto.UpdateEmployeeDto;
import com.ws.hw1.controller.post.dto.PostDto;
import com.ws.hw1.exceptionhandler.ErrorDto;
import com.ws.hw1.model.Employee;
import com.ws.hw1.model.JobType;
import com.ws.hw1.service.argument.CreateEmployeeArgument;
import com.ws.hw1.service.argument.CreatePostArgument;
import com.ws.hw1.service.employee.EmployeeService;
import com.ws.hw1.service.employee.SearchParams;
import com.ws.hw1.service.post.PostService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@AutoConfigureWebTestClient
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class EmployeeControllerIT {
    private final ObjectMapper objectMapper = new ObjectMapper();
    @Autowired
    private WebTestClient webTestClient;
    @Autowired
    private EmployeeService employeeService;
    @Autowired
    private PostService postService;

    @BeforeEach
    void setUpEmployees() throws IOException {
        CreatePostArgument argument = CreatePostArgument.builder()
                                                        .name("Tech Writer")
                                                        .build();
        postService.create(argument);

        CreateEmployeeArgument employee = objectMapper.readValue(new File(Objects.requireNonNull(EmployeeControllerIT.class
                                                                                         .getClassLoader()
                                                                                         .getResource("employee.json"))
                                                                                 .getFile()),
                CreateEmployeeArgument.class);

        employeeService.create(employee);
    }

    private Employee getFirst() {
        return employeeService.getAll(new SearchParams(null, null)).get(0);
    }

    private UUID getPostId() {
        return postService.getAll().get(0).getId();
    }

    @Test
    void create() throws IOException {
        //Arrange
        CreateEmployeeDto createEmployeeDto = objectMapper.readValue(new File(Objects.requireNonNull(EmployeeControllerIT.class
                                                                                             .getClassLoader()
                                                                                             .getResource("create_employee_dto.json"))
                                                                                     .getFile()),
                CreateEmployeeDto.class);
        createEmployeeDto.setPostId(getPostId());

        //Act
        EmployeeDto actual = webTestClient.post()
                                          .uri(uriBuilder -> uriBuilder.path("employee/create")
                                                                       .build())
                                          .bodyValue(createEmployeeDto)
                                          .exchange()
                                          //Assert
                                          .expectStatus()
                                          .isCreated()
                                          .expectBody(EmployeeDto.class)
                                          .returnResult()
                                          .getResponseBody();

        EmployeeDto expected = new EmployeeDto(UUID.fromString("720eb7c5-b0b1-4a33-ae68-4d0d5feab2d6"),
                "Геннадий",
                "Кузьмин",
                "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Cras sit \namet dictum felis, eu fringilla eros. Sed et gravida neque. Nullam at egestas \nerat. Mauris vitae convallis nulla. Aenean condimentum lectus magna. \nSuspendisse viverra quam non ante pellentesque, a euismod nunc dapibus. Duis \nsed congue erat",
                List.of("honest", "introvert", "like criticism", "love of learning", "pragmatism"),
                new PostDto(UUID.fromString("854ef89d-6c27-4635-926d-894d76a81707"), "Tech Writer"),
                new ContactsDto("+79247521321", "personalEmail@gmail.com", "workEmail@ya.ru"),
                JobType.FULL_TIME);

        Assertions.assertNotNull(actual.getId());
        assertThat(actual).usingRecursiveComparison()
                          .ignoringFields("id", "post.id")
                          .isEqualTo(expected);

    }

    @Test
    void get() {
        //Arrange
        Employee employee = getFirst();
        UUID id = employee.getId();

        //Act
        EmployeeDto actual = webTestClient.get()
                                          .uri("employee/{id}", id)
                                          .exchange()
                                          //Assert
                                          .expectStatus()
                                          .isOk()
                                          .expectBody(EmployeeDto.class)
                                          .returnResult()
                                          .getResponseBody();

        EmployeeDto expected = new EmployeeDto(UUID.fromString("720eb7c5-b0b1-4a33-ae68-4d0d5feab2d6"),
                "Геннадий",
                "Кузьмин",
                "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Cras sit \namet dictum felis, eu fringilla eros. Sed et gravida neque. Nullam at egestas \nerat. Mauris vitae convallis nulla. Aenean condimentum lectus magna. \nSuspendisse viverra quam non ante pellentesque, a euismod nunc dapibus. Duis \nsed congue erat",
                List.of("honest", "introvert", "like criticism", "love of learning", "pragmatism"),
                new PostDto(UUID.fromString("854ef89d-6c27-4635-926d-894d76a81707"), "Tech Writer"),
                new ContactsDto("+79247521321", "personalEmail@gmail.com", "workEmail@ya.ru"),
                JobType.FULL_TIME);

        assertThat(actual).usingRecursiveComparison()
                          .ignoringFields("id", "post.id")
                          .isEqualTo(expected);
    }

    @Test
    void tryGetAndGetNotFound() {
        //Arrange
        UUID id = UUID.randomUUID();

        ErrorDto errorDto = ErrorDto.builder()
                                    .message("The employee not found")
                                    .build();

        //Act
        webTestClient.get()
                     .uri("employee/{id}", id)
                     .exchange()
                     //Assert
                     .expectStatus()
                     .isNotFound()
                     .expectBody(ErrorDto.class)
                     .isEqualTo(errorDto);
    }

    @Test
    void update() throws IOException {
        //Arrange
        UpdateEmployeeDto updateEmployeeDto = objectMapper.readValue(new File(Objects.requireNonNull(EmployeeControllerIT.class
                                                                                             .getClassLoader()
                                                                                             .getResource("update_employee_dto.json"))
                                                                                     .getFile()),
                UpdateEmployeeDto.class);
        updateEmployeeDto.setPostId(getPostId());
        UUID id = getFirst().getId();


        //Act
        EmployeeDto actual = webTestClient.put()
                                          .uri("employee/{id}/update", id)
                                          .bodyValue(updateEmployeeDto)
                                          .exchange()
                                          //Assert
                                          .expectStatus()
                                          .isOk()
                                          .expectBody(EmployeeDto.class)
                                          .returnResult()
                                          .getResponseBody();

        EmployeeDto expected = new EmployeeDto(UUID.fromString("720eb7c4-b0b1-4a33-ae68-4d0d5feab2d6"),
                "Павел",
                "Кузьмин",
                "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Cras sit \namet dictum felis, eu fringilla eros. Sed et gravida neque. Nullam at egestas \nerat. Mauris vitae convallis nulla. Aenean condimentum lectus magna. \nSuspendisse viverra quam non ante pellentesque, a euismod nunc dapibus. Duis \nsed congue erat",
                List.of("honest", "introvert", "like criticism", "love of learning", "pragmatism"),
                new PostDto(UUID.fromString("854ef89d-6c27-4635-926d-894d76a81707"), "Tech Writer"),
                new ContactsDto("+79247521321", "personalEmail@gmail.com", "workEmail@ya.ru"),
                JobType.FULL_TIME);

        assertThat(actual).usingRecursiveComparison()
                          .ignoringFields("id", "post.id", "")
                          .isEqualTo(expected);
    }

    @Test
    void tryUpdateAndGetNotFound() throws IOException {
        //Arrange
        UpdateEmployeeDto employeeDto = objectMapper.readValue(new File(Objects.requireNonNull(EmployeeControllerIT.class
                                                                                       .getClassLoader()
                                                                                       .getResource("update_employee_dto.json"))
                                                                               .getFile()),
                UpdateEmployeeDto.class);
        UUID id = UUID.randomUUID();

        ErrorDto errorDto = ErrorDto.builder()
                                    .message("The post not found")
                                    .build();

        //Act
        webTestClient.put()
                     .uri("employee/{id}/update", id)
                     .bodyValue(employeeDto)
                     .exchange()
                     //Assert
                     .expectStatus()
                     .isNotFound()
                     .expectBody(ErrorDto.class)
                     .isEqualTo(errorDto);
    }

    @Test
    void getAll() {
        //Arrange

        //Act
        List<EmployeeDto> actual = webTestClient.get()
                                                .uri(uriBuilder -> uriBuilder.path("employee/list")
                                                                             .build())
                                                .exchange()
                                                //Assert
                                                .expectStatus()
                                                .isOk()
                                                .expectBodyList(EmployeeDto.class)
                                                .returnResult()
                                                .getResponseBody();

        EmployeeDto expected = new EmployeeDto(UUID.fromString("720eb7c5-b0b1-4a33-ae68-4d0d5feab2d6"),
                "Геннадий",
                "Кузьмин",
                "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Cras sit \namet dictum felis, eu fringilla eros. Sed et gravida neque. Nullam at egestas \nerat. Mauris vitae convallis nulla. Aenean condimentum lectus magna. \nSuspendisse viverra quam non ante pellentesque, a euismod nunc dapibus. Duis \nsed congue erat",
                List.of("honest", "introvert", "like criticism", "love of learning", "pragmatism"),
                new PostDto(UUID.fromString("854ef89d-6c27-4635-926d-894d76a81707"), "Tech Writer"),
                new ContactsDto("+79247521321", "personalEmail@gmail.com", "workEmail@ya.ru"),
                JobType.FULL_TIME);

        assertThat(actual.get(0)).usingRecursiveComparison()
                                 .ignoringFields("id", "post.id")
                                 .isEqualTo(expected);
    }

    @Test
    void getAllFilterByFirstName() {
        //Arrange

        //Act
        List<EmployeeDto> actual = webTestClient.get()
                                                .uri(uriBuilder -> uriBuilder.path("employee/list")
                                                                             .queryParam("name", "Геннадий")
                                                                             .build())
                                                .exchange()
                                                //Assert
                                                .expectStatus()
                                                .isOk()
                                                .expectBodyList(EmployeeDto.class)
                                                .returnResult()
                                                .getResponseBody();

        EmployeeDto expected = new EmployeeDto(UUID.fromString("720eb7c5-b0b1-4a33-ae68-4d0d5feab2d6"),
                "Геннадий",
                "Кузьмин",
                "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Cras sit \namet dictum felis, eu fringilla eros. Sed et gravida neque. Nullam at egestas \nerat. Mauris vitae convallis nulla. Aenean condimentum lectus magna. \nSuspendisse viverra quam non ante pellentesque, a euismod nunc dapibus. Duis \nsed congue erat",
                List.of("honest", "introvert", "like criticism", "love of learning", "pragmatism"),
                new PostDto(UUID.fromString("854ef89d-6c27-4635-926d-894d76a81707"), "Tech Writer"),
                new ContactsDto("+79247521321", "personalEmail@gmail.com", "workEmail@ya.ru"),
                JobType.FULL_TIME);

        assertThat(actual.get(0)).usingRecursiveComparison()
                                 .ignoringFields("id", "post.id")
                                 .isEqualTo(expected);
    }

    @Test
    void getAllFilterByLastName() {
        //Arrange

        //Act
        List<EmployeeDto> actual = webTestClient.get()
                                                .uri(uriBuilder -> uriBuilder.path("employee/list")
                                                                             .queryParam("name", "Кузьмин")
                                                                             .build())
                                                .exchange()
                                                //Assert
                                                .expectStatus()
                                                .isOk()
                                                .expectBodyList(EmployeeDto.class)
                                                .returnResult()
                                                .getResponseBody();

        EmployeeDto expected = new EmployeeDto(UUID.fromString("720eb7c4-b0b1-4a33-ae68-4d0d5feab2d6"),
                "Геннадий",
                "Кузьмин",
                "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Cras sit \namet dictum felis, eu fringilla eros. Sed et gravida neque. Nullam at egestas \nerat. Mauris vitae convallis nulla. Aenean condimentum lectus magna. \nSuspendisse viverra quam non ante pellentesque, a euismod nunc dapibus. Duis \nsed congue erat",
                List.of("honest", "introvert", "like criticism", "love of learning", "pragmatism"),
                new PostDto(UUID.fromString("854ef89d-6c27-4635-926d-894d76a81707"), "Tech Writer"),
                new ContactsDto("+79247521321", "personalEmail@gmail.com", "workEmail@ya.ru"),
                JobType.FULL_TIME);

        assertThat(actual.get(0)).usingRecursiveComparison()
                                 .ignoringFields("id", "post.id")
                                 .isEqualTo(expected);
    }

    @Test
    void getAllIncorrectFilter() {
        //Arrange

        //Act
        List<EmployeeDto> employeeDto = webTestClient.get()
                                                     .uri(uriBuilder -> uriBuilder.path("employee/list")
                                                                                  .queryParam("postId", "762d15a5-3bc9-43ef-ae96-02a680a557d1")
                                                                                  .build())
                                                     .exchange()
                                                     //Assert
                                                     .expectStatus()
                                                     .isOk()
                                                     .expectBodyList(EmployeeDto.class)
                                                     .returnResult()
                                                     .getResponseBody();

        Assertions.assertEquals(0, employeeDto.size());
    }

    @Test
    void getAllFilterByNameAndPostId() {
        //Arrange

        //Act
        List<EmployeeDto> actual = webTestClient.get()
                                                .uri(uriBuilder -> uriBuilder.path("employee/list")
                                                                             .queryParam("name", "Кузьмин")
                                                                             .queryParam("postId", "854ef89d-6c27-4635-926d-894d76a81707")
                                                                             .build())
                                                .exchange()
                                                //Assert
                                                .expectStatus()
                                                .isOk()
                                                .expectBodyList(EmployeeDto.class)
                                                .returnResult()
                                                .getResponseBody();

        EmployeeDto expected = new EmployeeDto(UUID.fromString("720eb7c5-b0b1-4a33-ae68-4d0d5feab2d6"),
                "Геннадий",
                "Кузьмин",
                "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Cras sit \namet dictum felis, eu fringilla eros. Sed et gravida neque. Nullam at egestas \nerat. Mauris vitae convallis nulla. Aenean condimentum lectus magna. \nSuspendisse viverra quam non ante pellentesque, a euismod nunc dapibus. Duis \nsed congue erat",
                List.of("honest", "introvert", "like criticism", "love of learning", "pragmatism"),
                new PostDto(UUID.fromString("854ef89d-6c27-4635-926d-894d76a81707"), "Tech Writer"),
                new ContactsDto("+79247521321", "personalEmail@gmail.com", "workEmail@ya.ru"),
                JobType.FULL_TIME);

        assertThat(actual.get(0)).usingRecursiveComparison()
                                 .ignoringFields("id", "post.id")
                                 .isEqualTo(expected);
    }
}

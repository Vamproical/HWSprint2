package com.ws.hw1.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.database.rider.core.api.dataset.DataSet;
import com.github.database.rider.core.api.dataset.ExpectedDataSet;
import com.jupiter.tools.spring.test.postgres.annotation.meta.EnablePostgresIntegrationTest;
import com.ws.hw1.controller.employee.dto.ContactsDto;
import com.ws.hw1.controller.employee.dto.CreateEmployeeDto;
import com.ws.hw1.controller.employee.dto.EmployeeDto;
import com.ws.hw1.controller.employee.dto.UpdateEmployeeDto;
import com.ws.hw1.controller.post.dto.PostDto;
import com.ws.hw1.exceptionhandler.ErrorDto;
import com.ws.hw1.model.JobType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@AutoConfigureWebTestClient
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@EnablePostgresIntegrationTest
class EmployeeControllerIT {
    private final ObjectMapper objectMapper = new ObjectMapper();
    @Autowired
    private WebTestClient webTestClient;


    @Test
    @DataSet(cleanBefore = true, cleanAfter = true, value = "datasets/employee/create/create.json")
    @ExpectedDataSet(value = "datasets/employee/create/expected_create.json", ignoreCols = "characteristics")
    void create() throws IOException {
        //Arrange
        CreateEmployeeDto createEmployeeDto = objectMapper.readValue(new File(Objects.requireNonNull(EmployeeControllerIT.class
                                                                                                             .getClassLoader()
                                                                                                             .getResource("datasets/employee/create/create_employee_dto.json"))
                                                                                     .getFile()),
                                                                     CreateEmployeeDto.class);

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
                                               new PostDto(UUID.fromString("ebc19c4f-5bd8-4165-813f-2ac699134ad4"), "Senior Developer"),
                                               new ContactsDto("+79247521321", "personalEmail@gmail.com", "workEmail@ya.ru"),
                                               JobType.FULL_TIME);

        Assertions.assertNotNull(actual.getId());
        assertThat(actual).usingRecursiveComparison()
                          .ignoringFields("id")
                          .isEqualTo(expected);
    }

    @Test
    @DataSet(cleanBefore = true, cleanAfter = true, value = "datasets/employee/get/get.json")
    void get() {
        //Arrange
        UUID id = UUID.fromString("e5320e1e-e628-4e08-8389-32e1b28b2acd");

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

        EmployeeDto expected = new EmployeeDto(UUID.fromString("e5320e1e-e628-4e08-8389-32e1b28b2acd"),
                                               "Геннадий",
                                               "Кузьмин",
                                               "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Cras sit \namet dictum felis, eu fringilla eros. Sed et gravida neque. Nullam at egestas \nerat. Mauris vitae convallis nulla. Aenean condimentum lectus magna. \nSuspendisse viverra quam non ante pellentesque, a euismod nunc dapibus. Duis \nsed congue erat",
                                               List.of("honest", "introvert", "like criticism", "love of learning", "pragmatism"),
                                               new PostDto(UUID.fromString("ebc19c4f-5bd8-4165-813f-2ac699134ad4"), "Senior Developer"),
                                               new ContactsDto("+79247521321", "personalEmail@gmail.com", "workEmail@ya.ru"),
                                               JobType.FULL_TIME);

        Assertions.assertEquals(expected, actual);
    }

    @Test
    void tryGetAndGetNotFound() {
        //Arrange
        UUID id = UUID.randomUUID();

        ErrorDto actual = ErrorDto.builder()
                                  .timestamp(LocalDateTime.now())
                                  .message("The employee not found")
                                  .build();

        //Act
        ErrorDto expected = webTestClient.get()
                                         .uri("employee/{id}", id)
                                         .exchange()
                                         //Assert
                                         .expectStatus()
                                         .isNotFound()
                                         .expectBody(ErrorDto.class)
                                         .returnResult()
                                         .getResponseBody();

        assertThat(expected).usingRecursiveComparison()
                            .ignoringFields("timestamp")
                            .isEqualTo(actual);
    }

    @Test
    @DataSet(cleanBefore = true, cleanAfter = true, value = "datasets/employee/update/update.json")
    @ExpectedDataSet(value = "datasets/employee/update/expected_update.json")
    void update() throws IOException {
        //Arrange
        UpdateEmployeeDto updateEmployeeDto = objectMapper.readValue(new File(Objects.requireNonNull(EmployeeControllerIT.class
                                                                                                             .getClassLoader()
                                                                                                             .getResource("datasets/employee/update/update_employee_dto.json"))
                                                                                     .getFile()),
                                                                     UpdateEmployeeDto.class);
        UUID id = UUID.fromString("e5320e1e-e628-4e08-8389-32e1b28b2acd");


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

        EmployeeDto expected = new EmployeeDto(UUID.fromString("e5320e1e-e628-4e08-8389-32e1b28b2acd"),
                                               "Владислав",
                                               "Кузьмин",
                                               "",
                                               List.of("extrovert", "like criticism", "love of learning", "pragmatism"),
                                               new PostDto(UUID.fromString("ebc19c4f-5bd8-4165-813f-2ac699134ad4"), "Senior Developer"),
                                               new ContactsDto("+79247521123", "personalEmail@gmail.com", "workingEmail@ya.ru"),
                                               JobType.PART_TIME);

        assertThat(actual).usingRecursiveComparison()
                          .isEqualTo(expected);
    }

    @Test
    @DataSet(cleanBefore = true, cleanAfter = true, value = "datasets/employee/update/update.json")
    void tryUpdateAndGetNotFound() throws IOException {
        //Arrange
        UpdateEmployeeDto employeeDto = objectMapper.readValue(new File(Objects.requireNonNull(EmployeeControllerIT.class
                                                                                                       .getClassLoader()
                                                                                                       .getResource("datasets/employee/update/update_employee_dto.json"))
                                                                               .getFile()),
                                                               UpdateEmployeeDto.class);
        UUID id = UUID.randomUUID();

        ErrorDto actual = ErrorDto.builder()
                                  .timestamp(LocalDateTime.now())
                                  .message("The employee not found")
                                  .build();

        //Act
        ErrorDto expected = webTestClient.put()
                                         .uri("employee/{id}/update", id)
                                         .bodyValue(employeeDto)
                                         .exchange()
                                         //Assert
                                         .expectStatus()
                                         .isNotFound()
                                         .expectBody(ErrorDto.class)
                                         .returnResult()
                                         .getResponseBody();

        assertThat(expected).usingRecursiveComparison()
                            .ignoringFields("timestamp")
                            .isEqualTo(actual);
    }

    @Test
    @DataSet(cleanBefore = true, cleanAfter = true, value = "datasets/employee/get/get.json")
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

        EmployeeDto expected = new EmployeeDto(UUID.fromString("e5320e1e-e628-4e08-8389-32e1b28b2acd"),
                                               "Геннадий",
                                               "Кузьмин",
                                               "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Cras sit \namet dictum felis, eu fringilla eros. Sed et gravida neque. Nullam at egestas \nerat. Mauris vitae convallis nulla. Aenean condimentum lectus magna. \nSuspendisse viverra quam non ante pellentesque, a euismod nunc dapibus. Duis \nsed congue erat",
                                               List.of("honest", "introvert", "like criticism", "love of learning", "pragmatism"),
                                               new PostDto(UUID.fromString("ebc19c4f-5bd8-4165-813f-2ac699134ad4"), "Senior Developer"),
                                               new ContactsDto("+79247521321", "personalEmail@gmail.com", "workEmail@ya.ru"),
                                               JobType.FULL_TIME);

        assertThat(actual.get(0)).usingRecursiveComparison()
                                 .isEqualTo(expected);
    }

    @Test
    @DataSet(cleanBefore = true, cleanAfter = true, value = "datasets/employee/get/getAll.json")
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

        EmployeeDto expected = new EmployeeDto(UUID.fromString("e5320e1e-e628-4e08-8389-32e1b28b2acd"),
                                               "Геннадий",
                                               "Кузьмин",
                                               "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Cras sit \namet dictum felis, eu fringilla eros. Sed et gravida neque. Nullam at egestas \nerat. Mauris vitae convallis nulla. Aenean condimentum lectus magna. \nSuspendisse viverra quam non ante pellentesque, a euismod nunc dapibus. Duis \nsed congue erat",
                                               List.of("like criticism", "love of learning"),
                                               new PostDto(UUID.fromString("ebc19c4f-5bd8-4165-813f-2ac699134ad4"), "Senior Developer"),
                                               new ContactsDto("+79247521321", "personalEmail@gmail.com", "workEmail@ya.ru"),
                                               JobType.FULL_TIME);

        assertThat(actual.get(0)).usingRecursiveComparison()
                                 .ignoringFields("id", "post.id")
                                 .isEqualTo(expected);
    }

    @Test
    @DataSet(cleanBefore = true, cleanAfter = true, value = "datasets/employee/get/getAll.json")
    void getAllFilterByLastName() {
        //Arrange

        //Act
        List<EmployeeDto> actual = webTestClient.get()
                                                .uri(uriBuilder -> uriBuilder.path("employee/list")
                                                                             .queryParam("name", "Павлова")
                                                                             .build())
                                                .exchange()
                                                //Assert
                                                .expectStatus()
                                                .isOk()
                                                .expectBodyList(EmployeeDto.class)
                                                .returnResult()
                                                .getResponseBody();

        EmployeeDto expected = new EmployeeDto(UUID.fromString("bbea8e3e-64ab-4d71-a856-fb8bf46934cb"),
                                               "Кристина",
                                               "Павлова",
                                               "some description",
                                               List.of("work better alone", "honest"),
                                               new PostDto(UUID.fromString("58f5bf47-2766-40dd-b878-9e8635e9b2ee"), "Junior Developer"),
                                               new ContactsDto("+79241111111", "pavlova@rambler.ru", "work_pavlova@gmail.com"),
                                               JobType.CONTRACT);

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
    @DataSet(cleanBefore = true, cleanAfter = true, value = "datasets/employee/get/getAll.json")
    void getAllFilterByNameAndPostId() {
        //Arrange

        //Act
        List<EmployeeDto> actual = webTestClient.get()
                                                .uri(uriBuilder -> uriBuilder.path("employee/list")
                                                                             .queryParam("name", "Чичиков")
                                                                             .queryParam("postId", "43dd5802-d810-4d7c-8d1a-3ccb4675f4f4")
                                                                             .build())
                                                .exchange()
                                                //Assert
                                                .expectStatus()
                                                .isOk()
                                                .expectBodyList(EmployeeDto.class)
                                                .returnResult()
                                                .getResponseBody();

        EmployeeDto expected = new EmployeeDto(UUID.fromString("e8a9d19f-91dc-463a-b7ce-798a196f8cdf"),
                                               "Павел",
                                               "Чичиков",
                                               "text text",
                                               List.of("pragmatism", "extrovert", "well with others"),
                                               new PostDto(UUID.fromString("854ef89d-6c27-4635-926d-894d76a81707"), "Middle Developer"),
                                               new ContactsDto("+79247512123", "chichikov@gmail.com", "chichikov_work@ya.ru"),
                                               JobType.PART_TIME);

        assertThat(actual.get(0)).usingRecursiveComparison()
                                 .ignoringFields("id", "post.id")
                                 .isEqualTo(expected);
    }

    @Test
    @DataSet(cleanBefore = true, cleanAfter = true, value = "datasets/employee/delete/delete.json")
    @ExpectedDataSet(value = "datasets/employee/delete/expected_delete.json")
    void delete() {
        //Arrange
        UUID id = UUID.fromString("e5320e1e-e628-4e08-8389-32e1b28b2acd");

        //Act
        webTestClient.delete()
                     .uri("employee/{id}/delete", id)
                     .exchange()
                     //Assert
                     .expectStatus()
                     .isOk();
    }

    @Test
    void tryDeleteAndGetNotFound() {
        //Arrange
        UUID id = UUID.randomUUID();

        ErrorDto actual = ErrorDto.builder()
                                  .timestamp(LocalDateTime.now())
                                  .message("The employee not found")
                                  .build();

        //Act
        ErrorDto expected = webTestClient.delete()
                                         .uri("employee/{id}/delete", id)
                                         .exchange()
                                         //Assert
                                         .expectStatus()
                                         .isNotFound()
                                         .expectBody(ErrorDto.class)
                                         .returnResult()
                                         .getResponseBody();

        assertThat(expected).usingRecursiveComparison()
                            .ignoringFields("timestamp")
                            .isEqualTo(actual);
    }
}

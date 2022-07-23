package com.ws.hw1.controller;

import com.github.database.rider.core.api.dataset.DataSet;
import com.github.database.rider.core.api.dataset.ExpectedDataSet;
import com.jupiter.tools.spring.test.postgres.annotation.meta.EnablePostgresIntegrationTest;
import com.ws.hw1.controller.employee.dto.CreateEmployeeDto;
import com.ws.hw1.controller.employee.dto.EmployeeDto;
import com.ws.hw1.controller.employee.dto.UpdateEmployeeDto;
import com.ws.hw1.exceptionhandler.ErrorDto;
import com.ws.hw1.utils.ReadFileUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@AutoConfigureWebTestClient
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@EnablePostgresIntegrationTest
class EmployeeControllerIT {
    private final ReadFileUtil fileUtil = new ReadFileUtil();
    @Autowired
    private WebTestClient webTestClient;

    @Test
    @DataSet(cleanBefore = true, cleanAfter = true, value = "datasets/employee/create/create.json")
    @ExpectedDataSet(value = "datasets/employee/create/expected_create.json")
    void create() throws IOException {
        //Arrange
        CreateEmployeeDto createEmployeeDto = fileUtil.execute("datasets/employee/create/create_employee_dto.json",
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

        EmployeeDto expected = fileUtil.execute("datasets/employee/create/expected_employee_dto.json",
                                                EmployeeDto.class);

        Assertions.assertNotNull(actual.getId());
        assertThat(actual).usingRecursiveComparison()
                          .ignoringFields("id")
                          .isEqualTo(expected);
    }

    @Test
    @DataSet(cleanBefore = true, cleanAfter = true, value = "datasets/employee/get/get.json")
    void get() throws IOException {
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

        EmployeeDto expected = fileUtil.execute("datasets/employee/get/expected_employee_dto.json",
                                                EmployeeDto.class);

        Assertions.assertEquals(expected, actual);
    }

    @Test
    void tryGetAndGetNotFound() {
        //Arrange
        UUID id = UUID.randomUUID();

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

        ErrorDto actual = ErrorDto.builder()
                                  .timestamp(LocalDateTime.now())
                                  .message("The employee not found")
                                  .build();

        assertThat(expected).usingRecursiveComparison()
                            .ignoringFields("timestamp")
                            .isEqualTo(actual);
    }

    @Test
    @DataSet(cleanBefore = true, cleanAfter = true, value = "datasets/employee/update/update.json")
    @ExpectedDataSet(value = "datasets/employee/update/expected_update.json")
    void update() throws IOException {
        //Arrange
        UpdateEmployeeDto updateEmployeeDto = fileUtil.execute("datasets/employee/update/update_employee_dto.json",
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

        EmployeeDto expected = fileUtil.execute("datasets/employee/update/expected_employee_dto.json",
                                                EmployeeDto.class);

        assertThat(actual).usingRecursiveComparison()
                          .isEqualTo(expected);
    }


    @Test
    @DataSet(cleanBefore = true, cleanAfter = true, value = "datasets/employee/get/get.json")
    void getAll() throws IOException {
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

        EmployeeDto expected = fileUtil.execute("datasets/employee/get/expected_employee_dto.json",
                                                EmployeeDto.class);

        assertThat(actual.get(0)).usingRecursiveComparison()
                                 .isEqualTo(expected);
    }

    @ParameterizedTest
    @DataSet(cleanBefore = true, cleanAfter = true, value = "datasets/employee/get/getAll.json")
    @CsvSource({"name, Геннадий", "name, Кузьмин", "postId, ebc19c4f-5bd8-4165-813f-2ac699134ad4"})
    void getAllFilterByFirstNameOrLastNameOrPostId(String typeFilter, String filter) throws IOException {
        //Arrange

        //Act
        List<EmployeeDto> actual = webTestClient.get()
                                                .uri(uriBuilder -> uriBuilder.path("employee/list")
                                                                             .queryParam(typeFilter, filter)
                                                                             .build())
                                                .exchange()
                                                //Assert
                                                .expectStatus()
                                                .isOk()
                                                .expectBodyList(EmployeeDto.class)
                                                .returnResult()
                                                .getResponseBody();

        EmployeeDto expected = fileUtil.execute("datasets/employee/get/expected_employee_dto_for_get_all.json",
                                                EmployeeDto.class);

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
    void getAllFilterByNameAndPostId() throws IOException {
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

        EmployeeDto expected = fileUtil.execute("datasets/employee/get/expected_employee_dto_pavel.json",
                                                EmployeeDto.class);

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

}

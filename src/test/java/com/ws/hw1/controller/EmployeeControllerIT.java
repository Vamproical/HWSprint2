package com.ws.hw1.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ws.hw1.controller.employee.dto.ContactsDto;
import com.ws.hw1.controller.employee.dto.CreateEmployeeDto;
import com.ws.hw1.controller.employee.dto.EmployeeDto;
import com.ws.hw1.controller.employee.dto.UpdateEmployeeDto;
import com.ws.hw1.exceptionHandler.exception.NotFoundException;
import com.ws.hw1.model.Employee;
import com.ws.hw1.model.Post;
import com.ws.hw1.service.employee.EmployeeService;
import com.ws.hw1.service.employee.SearchParams;
import com.ws.hw1.service.post.PostService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@AutoConfigureWebTestClient
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class EmployeeControllerIT {
    List<Employee> employees = new ArrayList<>();
    @Autowired
    private WebTestClient webTestClient;
    @SpyBean
    private EmployeeService employeeService;
    @SpyBean
    private PostService postService;

    @BeforeEach
    void setUpEmployees() throws IOException {
        Employee[] employees1 = new ObjectMapper()
                .readValue(new File(EmployeeControllerIT.class
                                .getClassLoader()
                                .getResource("employees.json")
                                .getFile()),
                        Employee[].class);

        employees.addAll(List.of(employees1));
    }

    @Test
    void post() {
        //arrange
        Employee employee = employees.get(0);
        ContactsDto contactsDto = ContactsDto.builder()
                                             .phone(employee.getContacts().getPhone())
                                             .email(employee.getContacts().getEmail())
                                             .workEmail(employee.getContacts().getWorkEmail())
                                             .build();
        Post post = employee.getPost();
        doReturn(post).when(postService).get(any());

        CreateEmployeeDto createEmployeeDto = CreateEmployeeDto.builder()
                                                               .firstName(employee.getFirstName())
                                                               .lastName(employee.getLastName())
                                                               .description(employee.getDescription())
                                                               .characteristics(employee.getCharacteristics())
                                                               .postId(employee.getPost().getId())
                                                               .contacts(contactsDto)
                                                               .jobType(employee.getJobType())
                                                               .build();

        doReturn(employee).when(employeeService).create(any());

        //act
        EmployeeDto employeeDto = webTestClient.post()
                                               .uri(uriBuilder -> uriBuilder.path("employee/create")
                                                                            .build())
                                               .bodyValue(createEmployeeDto)
                                               .exchange()
                                               //assert
                                               .expectStatus()
                                               .isCreated()
                                               .expectBody(EmployeeDto.class)
                                               .returnResult()
                                               .getResponseBody();

        verify(postService).get(post.getId());
        verify(employeeService).create(any());
        assertEqualsEmployeeDtoAndEmployee(employeeDto, employee);
    }

    @Test
    void get() {
        //arrange
        Employee employee = employees.get(0);
        UUID id = employee.getId();
        doReturn(employee).when(employeeService).get(any());

        //act
        EmployeeDto employeeDto = webTestClient.get()
                                               .uri(uriBuilder -> uriBuilder.path("employee/" + id)
                                                                            .build())
                                               .exchange()
                                               //arrange
                                               .expectStatus()
                                               .isOk()
                                               .expectBody(EmployeeDto.class)
                                               .returnResult()
                                               .getResponseBody();

        verify(employeeService).get(id);
        assertEqualsEmployeeDtoAndEmployee(employeeDto, employee);
    }

    @Test
    void tryGetAndGetNotFound() {
        //arrange
        UUID id = UUID.randomUUID();
        doThrow(NotFoundException.class).when(employeeService).get(id);

        //act
        webTestClient.get()
                     .uri(uriBuilder -> uriBuilder.path("employee/" + id)
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
        //arrange
        Employee employee = employees.get(0);
        Employee excepted = employees.get(2);

        ContactsDto contactsDto = ContactsDto.builder()
                                             .phone(employee.getContacts().getPhone())
                                             .email(employee.getContacts().getEmail())
                                             .workEmail(employee.getContacts().getWorkEmail())
                                             .build();

        UpdateEmployeeDto updateEmployeeDto = UpdateEmployeeDto.builder()
                                                               .firstName("Павел")
                                                               .lastName(employee.getLastName())
                                                               .description(employee.getDescription())
                                                               .characteristics(employee.getCharacteristics())
                                                               .postId(employee.getPost().getId())
                                                               .contacts(contactsDto)
                                                               .jobType(employee.getJobType())
                                                               .build();
        UUID id = employee.getId();

        Post post = employee.getPost();

        doReturn(post).when(postService).get(any());
        doReturn(excepted).when(employeeService).update(any(), any());

        //act
        EmployeeDto employeeDto = webTestClient.put()
                                               .uri(uriBuilder -> uriBuilder.path("employee/" + id + "/update")
                                                                            .build())
                                               .bodyValue(updateEmployeeDto)
                                               .exchange()
                                               //assert
                                               .expectStatus()
                                               .isOk()
                                               .expectBody(EmployeeDto.class)
                                               .returnResult()
                                               .getResponseBody();

        verify(postService).get(post.getId());
        assertEqualsEmployeeDtoAndEmployee(employeeDto, excepted);
    }

    @Test
    void tryUpdateAndGetNotFound() {
        //arrange
        Employee employee = employees.get(0);

        ContactsDto contactsDto = ContactsDto.builder()
                                             .phone(employee.getContacts().getPhone())
                                             .email(employee.getContacts().getEmail())
                                             .workEmail(employee.getContacts().getWorkEmail())
                                             .build();
        UpdateEmployeeDto employeeDto = UpdateEmployeeDto.builder()
                                                         .firstName(employee.getFirstName())
                                                         .lastName(employee.getLastName())
                                                         .description(employee.getDescription())
                                                         .characteristics(employee.getCharacteristics())
                                                         .postId(employee.getPost().getId())
                                                         .contacts(contactsDto)
                                                         .jobType(employee.getJobType())
                                                         .build();
        UUID id = UUID.randomUUID();
        doThrow(NotFoundException.class).when(employeeService).update(any(), any());

        //act
        webTestClient.put()
                     .uri(uriBuilder -> uriBuilder.path("employee/" + id + "/update")
                                                  .build())
                     .bodyValue(employeeDto)
                     .exchange()
                     //arrange
                     .expectStatus()
                     .isNotFound()
                     .expectBody(String.class)
                     .isEqualTo("The specified id is not found");
    }

    @Test
    void getAll() {
        //arrange
        List<Employee> employee = List.of(employees.get(0), employees.get(1));

        doReturn(employee).when(employeeService).getAll(any());

        //act
        List<EmployeeDto> employeeDto = webTestClient.get()
                                                     .uri(uriBuilder -> uriBuilder.path("employee/list")
                                                                                  .build())
                                                     .exchange()
                                                     //arrange
                                                     .expectStatus()
                                                     .isOk()
                                                     .expectBodyList(EmployeeDto.class)
                                                     .returnResult()
                                                     .getResponseBody();

        verify(employeeService).getAll(new SearchParams(null, null));
        assertEqualsEmployeeDtoAndEmployee(employeeDto.get(0), employee.get(0));
        assertEqualsEmployeeDtoAndEmployee(employeeDto.get(1), employee.get(1));
    }

    @Test
    void getAllFilterByFirstName() {
        //arrange
        List<Employee> employee = List.of(employees.get(2));

        doReturn(employee).when(employeeService).getAll(any());

        //act
        List<EmployeeDto> employeeDto = webTestClient.get()
                                                     .uri(uriBuilder -> uriBuilder.path("employee/list")
                                                                                  .queryParam("name", "Павел")
                                                                                  .build())
                                                     .exchange()
                                                     //arrange
                                                     .expectStatus()
                                                     .isOk()
                                                     .expectBodyList(EmployeeDto.class)
                                                     .returnResult()
                                                     .getResponseBody();

        verify(employeeService).getAll(new SearchParams("Павел", null));
        assertEqualsEmployeeDtoAndEmployee(employeeDto.get(0), employee.get(0));
    }

    @Test
    void getAllFilterByLastName() {
        //arrange
        List<Employee> employee = List.of(employees.get(2));

        doReturn(employee).when(employeeService).getAll(any());

        //act
        List<EmployeeDto> employeeDto = webTestClient.get()
                                                     .uri(uriBuilder -> uriBuilder.path("employee/list")
                                                                                  .queryParam("name", "Кузьмин")
                                                                                  .build())
                                                     .exchange()
                                                     //arrange
                                                     .expectStatus()
                                                     .isOk()
                                                     .expectBodyList(EmployeeDto.class)
                                                     .returnResult()
                                                     .getResponseBody();

        verify(employeeService).getAll(new SearchParams("Кузьмин", null));
        assertEqualsEmployeeDtoAndEmployee(employeeDto.get(0), employee.get(0));
    }

    @Test
    void getAllFilterByPostId() {
        //arrange
        List<Employee> employee = List.of(employees.get(1));

        doReturn(employee).when(employeeService).getAll(any());

        //act
        List<EmployeeDto> employeeDto = webTestClient.get()
                                                     .uri(uriBuilder -> uriBuilder.path("employee/list")
                                                                                  .queryParam("postId", "762d15a5-3bc9-43ef-ae96-02a680a557d0")
                                                                                  .build())
                                                     .exchange()
                                                     //arrange
                                                     .expectStatus()
                                                     .isOk()
                                                     .expectBodyList(EmployeeDto.class)
                                                     .returnResult()
                                                     .getResponseBody();

        verify(employeeService).getAll(new SearchParams(null, UUID.fromString("762d15a5-3bc9-43ef-ae96-02a680a557d0")));
        assertEqualsEmployeeDtoAndEmployee(employeeDto.get(0), employee.get(0));
    }

    @Test
    void getAllIncorrectFilter() {
        //arrange
        List<Employee> employee = Collections.EMPTY_LIST;

        doReturn(employee).when(employeeService).getAll(any());

        //act
        List<EmployeeDto> employeeDto = webTestClient.get()
                                                     .uri(uriBuilder -> uriBuilder.path("employee/list")
                                                                                  .queryParam("postId", "762d15a5-3bc9-43ef-ae96-02a680a557d1")
                                                                                  .build())
                                                     .exchange()
                                                     //arrange
                                                     .expectStatus()
                                                     .isOk()
                                                     .expectBodyList(EmployeeDto.class)
                                                     .returnResult()
                                                     .getResponseBody();

        verify(employeeService).getAll(new SearchParams(null, UUID.fromString("762d15a5-3bc9-43ef-ae96-02a680a557d1")));
        Assertions.assertEquals(0, employeeDto.size());
    }

    @Test
    void getAllFilterByNameAndPostId() {
        //arrange
        List<Employee> employee = List.of(employees.get(0));

        doReturn(employee).when(employeeService).getAll(any());

        //act
        List<EmployeeDto> employeeDto = webTestClient.get()
                                                     .uri(uriBuilder -> uriBuilder.path("employee/list")
                                                                                  .queryParam("name", "Кузьмин")
                                                                                  .queryParam("postId", "854ef89d-6c27-4635-926d-894d76a81707")
                                                                                  .build())
                                                     .exchange()
                                                     //arrange
                                                     .expectStatus()
                                                     .isOk()
                                                     .expectBodyList(EmployeeDto.class)
                                                     .returnResult()
                                                     .getResponseBody();

        verify(employeeService).getAll(new SearchParams("Кузьмин", UUID.fromString("854ef89d-6c27-4635-926d-894d76a81707")));
        assertEqualsEmployeeDtoAndEmployee(employeeDto.get(0), employee.get(0));
    }

    private void assertEqualsEmployeeDtoAndEmployee(EmployeeDto employeeDto, Employee employee) {
        Assertions.assertEquals(employeeDto.getFirstName(), employee.getFirstName());
        Assertions.assertEquals(employeeDto.getLastName(), employee.getLastName());
        Assertions.assertEquals(employeeDto.getDescription(), employee.getDescription());
        Assertions.assertEquals(employeeDto.getCharacteristics(), employee.getCharacteristics());
        Assertions.assertEquals(employeeDto.getPost(), employee.getPost());
        Assertions.assertEquals(employeeDto.getContacts(), employee.getContacts());
        Assertions.assertEquals(employeeDto.getJobType(), employee.getJobType());
    }
}

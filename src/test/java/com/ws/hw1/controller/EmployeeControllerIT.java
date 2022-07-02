package com.ws.hw1.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ws.hw1.controller.employee.dto.ContactsDto;
import com.ws.hw1.controller.employee.dto.CreateEmployeeDto;
import com.ws.hw1.controller.employee.dto.EmployeeDto;
import com.ws.hw1.controller.employee.dto.UpdateEmployeeDto;
import com.ws.hw1.controller.post.dto.PostDto;
import com.ws.hw1.exceptionhandler.ErrorDto;
import com.ws.hw1.exceptionhandler.exception.NotFoundException;
import com.ws.hw1.model.Employee;
import com.ws.hw1.model.JobType;
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
import java.util.*;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@AutoConfigureWebTestClient
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class EmployeeControllerIT {
    private final List<Employee> employees = new ArrayList<>();
    private final ObjectMapper objectMapper = new ObjectMapper();
    @Autowired
    private WebTestClient webTestClient;
    @SpyBean
    private EmployeeService employeeService;
    @SpyBean
    private PostService postService;

    @BeforeEach
    void setUpEmployees() throws IOException {
        Employee[] employees1 = objectMapper.readValue(new File(EmployeeControllerIT.class
                        .getClassLoader()
                        .getResource("employees.json")
                        .getFile()),
                Employee[].class);

        employees.addAll(List.of(employees1));
    }

    @Test
    void create() throws IOException {
        //Arrange
        Employee employee = employees.get(0);

        Post post = employee.getPost();

        doReturn(post).when(postService).getExisting(any());

        CreateEmployeeDto createEmployeeDto = objectMapper.readValue(new File(Objects.requireNonNull(EmployeeControllerIT.class
                                                                                             .getClassLoader()
                                                                                             .getResource("create_employee_dto.json"))
                                                                                     .getFile()),
                CreateEmployeeDto.class);

        doReturn(employee).when(employeeService).create(any());

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

        verify(employeeService).create(any());
        Assertions.assertEquals(expected, actual);

    }

    @Test
    void get() {
        //Arrange
        Employee employee = employees.get(0);
        UUID id = employee.getId();

        doReturn(employee).when(employeeService).getExisting(any());

        //Act
        EmployeeDto actual = webTestClient.get()
                                          .uri("employee/{id}", id)
                                          .exchange()
                                          //Arrange
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

        verify(employeeService).getExisting(id);
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void tryGetAndGetNotFound() {
        //Arrange
        UUID id = UUID.randomUUID();
        doThrow(new NotFoundException("The employee not found")).when(employeeService).getExisting(id);

        ErrorDto errorDto = ErrorDto.builder()
                                    .message("The employee not found")
                                    .build();

        //Act
        webTestClient.get()
                     .uri("employee/{id}", id)
                     .exchange()
                     //Arrange
                     .expectStatus()
                     .isNotFound()
                     .expectBody(ErrorDto.class)
                     .isEqualTo(errorDto);
    }

    @Test
    void update() {
        //Arrange
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

        doReturn(post).when(postService).getExisting(any());
        doReturn(excepted).when(employeeService).update(any(), any());

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

        verify(postService).getExisting(post.getId());
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void tryUpdateAndGetNotFound() {
        //Arrange
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

        ErrorDto errorDto = ErrorDto.builder()
                                    .message("The post not found")
                                    .build();

        //Act
        webTestClient.put()
                     .uri("employee/{id}/update", id)
                     .bodyValue(employeeDto)
                     .exchange()
                     //Arrange
                     .expectStatus()
                     .isNotFound()
                     .expectBody(ErrorDto.class)
                     .isEqualTo(errorDto);
    }

    @Test
    void getAll() {
        //Arrange
        List<Employee> employee = List.of(employees.get(0), employees.get(1));

        doReturn(employee).when(employeeService).getAll(any());

        //Act
        List<EmployeeDto> actual = webTestClient.get()
                                                .uri(uriBuilder -> uriBuilder.path("employee/list")
                                                                             .build())
                                                .exchange()
                                                //Arrange
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

        EmployeeDto expected1 = new EmployeeDto(UUID.fromString("720eb7c5-b0b1-4a33-ae68-4d0d5feab2d5"),
                "Ivan",
                "Ivanov",
                "",
                List.of("active", "cynical", "hard-working", "enthusiastic"),
                new PostDto(UUID.fromString("762d15a5-3bc9-43ef-ae96-02a680a557d0"), "Lead Developer"),
                new ContactsDto("+79247121321", "firstEmail@mail.ru", "secondEmail@gmail.com"),
                JobType.CONTRACT);


        verify(employeeService).getAll(new SearchParams(null, null));
        Assertions.assertEquals(expected, actual.get(0));
        Assertions.assertEquals(expected1, actual.get(1));
    }

    @Test
    void getAllFilterByFirstName() {
        //Arrange
        List<Employee> employee = List.of(employees.get(2));

        doReturn(employee).when(employeeService).getAll(any());

        //Act
        List<EmployeeDto> actual = webTestClient.get()
                                                .uri(uriBuilder -> uriBuilder.path("employee/list")
                                                                             .queryParam("name", "Павел")
                                                                             .build())
                                                .exchange()
                                                //Arrange
                                                .expectStatus()
                                                .isOk()
                                                .expectBodyList(EmployeeDto.class)
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

        verify(employeeService).getAll(new SearchParams("Павел", null));
        Assertions.assertEquals(expected, actual.get(0));
    }

    @Test
    void getAllFilterByLastName() {
        //Arrange
        List<Employee> employee = List.of(employees.get(2));

        doReturn(employee).when(employeeService).getAll(any());

        //Act
        List<EmployeeDto> actual = webTestClient.get()
                                                .uri(uriBuilder -> uriBuilder.path("employee/list")
                                                                             .queryParam("name", "Кузьмин")
                                                                             .build())
                                                .exchange()
                                                //Arrange
                                                .expectStatus()
                                                .isOk()
                                                .expectBodyList(EmployeeDto.class)
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

        verify(employeeService).getAll(new SearchParams("Кузьмин", null));
        Assertions.assertEquals(expected, actual.get(0));
    }

    @Test
    void getAllFilterByPostId() {
        //Arrange
        List<Employee> employee = List.of(employees.get(1));

        doReturn(employee).when(employeeService).getAll(any());

        //Act
        List<EmployeeDto> actual = webTestClient.get()
                                                .uri(uriBuilder -> uriBuilder.path("employee/list")
                                                                             .queryParam("postId", "762d15a5-3bc9-43ef-ae96-02a680a557d0")
                                                                             .build())
                                                .exchange()
                                                //Arrange
                                                .expectStatus()
                                                .isOk()
                                                .expectBodyList(EmployeeDto.class)
                                                .returnResult()
                                                .getResponseBody();


        EmployeeDto expected = new EmployeeDto(UUID.fromString("720eb7c5-b0b1-4a33-ae68-4d0d5feab2d5"),
                "Ivan",
                "Ivanov",
                "",
                List.of("active", "cynical", "hard-working", "enthusiastic"),
                new PostDto(UUID.fromString("762d15a5-3bc9-43ef-ae96-02a680a557d0"), "Lead Developer"),
                new ContactsDto("+79247121321", "firstEmail@mail.ru", "secondEmail@gmail.com"),
                JobType.CONTRACT);

        verify(employeeService).getAll(new SearchParams(null, UUID.fromString("762d15a5-3bc9-43ef-ae96-02a680a557d0")));
        Assertions.assertEquals(expected, actual.get(0));
    }

    @Test
    void getAllIncorrectFilter() {
        //Arrange
        List<Employee> employee = Collections.EMPTY_LIST;

        doReturn(employee).when(employeeService).getAll(any());

        //Act
        List<EmployeeDto> employeeDto = webTestClient.get()
                                                     .uri(uriBuilder -> uriBuilder.path("employee/list")
                                                                                  .queryParam("postId", "762d15a5-3bc9-43ef-ae96-02a680a557d1")
                                                                                  .build())
                                                     .exchange()
                                                     //Arrange
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
        //Arrange
        List<Employee> employee = List.of(employees.get(0));

        doReturn(employee).when(employeeService).getAll(any());

        //Act
        List<EmployeeDto> actual = webTestClient.get()
                                                .uri(uriBuilder -> uriBuilder.path("employee/list")
                                                                             .queryParam("name", "Кузьмин")
                                                                             .queryParam("postId", "854ef89d-6c27-4635-926d-894d76a81707")
                                                                             .build())
                                                .exchange()
                                                //Arrange
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

        verify(employeeService).getAll(new SearchParams("Кузьмин", UUID.fromString("854ef89d-6c27-4635-926d-894d76a81707")));
        Assertions.assertEquals(expected, actual.get(0));
    }

}

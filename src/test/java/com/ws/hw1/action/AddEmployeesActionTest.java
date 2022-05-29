package com.ws.hw1.action;

import com.ws.hw1.mapper.EmployeeMapper;
import com.ws.hw1.mapper.EmployeeMapperImpl;
import com.ws.hw1.model.Employee;
import com.ws.hw1.model.EmployeeFromFile;
import com.ws.hw1.model.Post;
import com.ws.hw1.service.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.mockito.Mockito.*;

class AddEmployeesActionTest {

    public static final Employee EMPLOYEE = new Employee(
            "Иван",
            "Иванов",
            null,
            List.of("some characteristics"),
            new Post(UUID.fromString("854ef89d-6c27-4635-926d-894d76a81707"), "Tech lead"));

    public static final Employee EMPLOYEE1 = new Employee(
            "Геннадий",
            "Кузьмин",
            "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Cras sitamet dictum felis, eu fringilla eros. Sed et gravida neque. Nullam at egestas erat. Mauris vitae convallis nulla. Aenean condimentum lectus magna. Suspendisse viverra quam non ante pellentesque, a euismod nunc dapibus. Duis sed congue erat",
            List.of("honest",
                    "introvert",
                    "like criticism",
                    "love of Learning",
                    "pragmatism"),
            new Post(UUID.fromString("762d15a5-3bc9-43ef-ae96-02a680a557d0"), "Tech Writer"));

    public static final EmployeeFromFile EMPLOYEE_FROM_FILE = new EmployeeFromFile(
            "Иван",
            "Иванов",
            null,
            List.of("some characteristics"),
            "854ef89d-6c27-4635-926d-894d76a81707");

    public static final EmployeeFromFile EMPLOYEE_FROM_FILE1 = new EmployeeFromFile(
            "Генадий",
            "Кузьмин",
            "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Cras sitamet dictum felis, eu fringilla eros. Sed et gravida neque. Nullam at egestas erat. Mauris vitae convallis nulla. Aenean condimentum lectus magna. Suspendisse viverra quam non ante pellentesque, a euismod nunc dapibus. Duis sed congue erat",
            List.of("honest",
                    "introvert",
                    "like criticism",
                    "love of Learning",
                    "pragmatism"),
            "762d15a5-3bc9-43ef-ae96-02a680a557d0");

    @Test
    void addEmployeesFromFile() {
        //Arrange
        List<Employee> expected = List.of(EMPLOYEE, EMPLOYEE1);
        List<Post> posts = List.of(new Post(UUID.fromString("854ef89d-6c27-4635-926d-894d76a81707"), "Tech lead"),
                new Post(UUID.fromString("762d15a5-3bc9-43ef-ae96-02a680a557d0"), "Tech Writer"));
        List<EmployeeFromFile> parsed = List.of(EMPLOYEE_FROM_FILE, EMPLOYEE_FROM_FILE1);
        EmployeeService employeeService = mock(EmployeeServiceImpl.class);
        ParseService parseService = mock(ParseServiceImpl.class);
        PostService postService = new PostService();
        EmployeeMapper employeeMapper = mock(EmployeeMapperImpl.class);
        AddEmployeesAction action = new AddEmployeesAction(parseService,
                postService,
                employeeService,
                employeeMapper);
        File file = new File("src/test/resources/employees.json");
        //Act
        when(parseService.parseJsonFile(file)).thenReturn(parsed);
        when(employeeMapper.toEmployee(EMPLOYEE_FROM_FILE, posts.get(0))).thenReturn(EMPLOYEE);
        when(employeeMapper.toEmployee(EMPLOYEE_FROM_FILE1, posts.get(1))).thenReturn(EMPLOYEE1);
        doNothing().when(employeeService).addEmployees(expected);
        action.addEmployeesFromFile(file);
        //Assert
        verify(parseService).parseJsonFile(file);
        verify(employeeMapper, times(2)).toEmployee(any(), any());
        verify(employeeService).addEmployees(expected);
    }
}
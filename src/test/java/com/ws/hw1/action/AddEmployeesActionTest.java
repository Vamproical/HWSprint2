package com.ws.hw1.action;

import com.ws.hw1.mapper.EmployeeMapper;
import com.ws.hw1.mapper.EmployeeMapperImpl;
import com.ws.hw1.parser.EmployeeFromFile;
import com.ws.hw1.parser.Parser;
import com.ws.hw1.parser.ParserFromJson;
import com.ws.hw1.service.employee.EmployeeService;
import com.ws.hw1.service.employee.EmployeeServiceImpl;
import com.ws.hw1.service.post.PostService;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.mockito.Mockito.mock;

class AddEmployeesActionTest {
    EmployeeService employeeService = mock(EmployeeServiceImpl.class);
    Parser parseService = mock(ParserFromJson.class);
    PostService postService = mock(PostService.class);
    EmployeeMapper employeeMapper = mock(EmployeeMapperImpl.class);
    AddEmployeesAction action = new AddEmployeesAction(parseService,
            postService,
            employeeService,
            employeeMapper);
//    public static final Employee EMPLOYEE = new Employee(
//            "Иван",
//            "Иванов",
//            null,
//            List.of("some characteristics"),
//            new Post(UUID.fromString("854ef89d-6c27-4635-926d-894d76a81707"), "Tech lead"));
//
//    public static final Employee EMPLOYEE1 = new Employee(
//            "Геннадий",
//            "Кузьмин",
//            "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Cras sitamet dictum felis, eu fringilla eros. Sed et gravida neque. Nullam at egestas erat. Mauris vitae convallis nulla. Aenean condimentum lectus magna. Suspendisse viverra quam non ante pellentesque, a euismod nunc dapibus. Duis sed congue erat",
//            List.of("honest",
//                    "introvert",
//                    "like criticism",
//                    "love of Learning",
//                    "pragmatism"),
//            new Post(UUID.fromString("762d15a5-3bc9-43ef-ae96-02a680a557d0"), "Tech Writer"));

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
//        List<Employee> expected = List.of(EMPLOYEE, EMPLOYEE1);
//        List<Post> posts = List.of(new Post(UUID.fromString("854ef89d-6c27-4635-926d-894d76a81707"), "Tech lead"),
//                new Post(UUID.fromString("762d15a5-3bc9-43ef-ae96-02a680a557d0"), "Tech Writer"));
//        List<EmployeeFromFile> parsed = List.of(EMPLOYEE_FROM_FILE, EMPLOYEE_FROM_FILE1);

//        File file = new File(AddEmployeesActionTest.class.getClassLoader().getResource("employees.json").getFile());
//        when(parseService.parse(file)).thenReturn(parsed);
//        when(postService.get(UUID.fromString("854ef89d-6c27-4635-926d-894d76a81707"))).thenReturn(posts.get(0));
//        when(postService.get(UUID.fromString("762d15a5-3bc9-43ef-ae96-02a680a557d0"))).thenReturn(posts.get(1));
//        when(employeeMapper.toEmployee(any(), any())).thenReturn(EMPLOYEE, EMPLOYEE1);

//        //Act
//        action.execute(file);
//        //Assert
//        verify(parseService).parse(file);
//        verify(employeeService).addEmployees(expected);

//        verify(employeeMapper).toEmployee(EMPLOYEE_FROM_FILE, posts.get(0));
//        verify(employeeMapper).toEmployee(EMPLOYEE_FROM_FILE1, posts.get(1));
    }
}
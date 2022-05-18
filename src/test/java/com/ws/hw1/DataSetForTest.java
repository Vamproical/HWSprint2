package com.ws.hw1;

import java.util.List;

public class DataSetForTest {
    public static final List<String> stringsFromFile = List.of(
            "firstName: Геннадий\r\n" +
                    "lastName: Кузьмин\r\n" +
                    "description: Lorem ipsum dolor sit amet, consectetur adipiscing elit. Cras sitamet dictum felis, eu fringilla eros. Sed et gravida neque. Nullam at egestas erat. Mauris vitae convallis nulla. Aenean condimentum lectus magna. Suspendisse viverra quam non ante pellentesque, a euismod nunc dapibus. Duis sed congue erat\r\n" +
                    "characteristics: honest, introvert, like criticism, love of Learning, pragmatism\r\n" +
                    "postId: 854ef89d-6c27-4635-926d-894d76a81707",
            "firstName: Иванов\r\n" +
                    "lastName: Иван\r\n" +
                    "description:\r\n" +
                    "characteristics: some characteristics\r\n" +
                    "postId: 762d15a5-3bc9-43ef-ae96-02a680a557d0");
    public static final List<String> incorrectStringsFromFile = List.of(
            "firstName: " +
                    "lastName: Кузьмин\r\n" +
                    "description: Lorem ipsum dolor sit amet, consectetur adipiscing elit. Cras sitamet dictum felis, eu fringilla eros. Sed et gravida neque. Nullam at egestas erat. Mauris vitae convallis nulla. Aenean condimentum lectus magna. Suspendisse viverra quam non ante pellentesque, a euismod nunc dapibus. Duis sed congue erat\r\n" +
                    "characteristics: honest, introvert, like criticism, love of Learning, pragmatism\r\n" +
                    "postId: 854ef89d-6c27-4635-926d-894d76a81707",
            "firstName: Иванов\r\n" +
                    "lastName: Иван\r\n" +
                    "description:\r\n" +
                    "characteristics: some characteristics\r\n" +
                    "postId: 762d15a5-3bc9-43ef-ae96-02a680a557d0");

    public static final List<String> dataForTheTestWithIncorrectReadFile = List.of(
            "firstName: Геннадий\n" +
                    "lastName: Кузьмин\n" +
                    "description: Lorem ipsum dolor sit amet, consectetur adipiscing elit. Cras sitamet dictum felis, eu fringilla eros. Sed et gravida neque. Nullam at egestas erat. Mauris vitae convallis nulla. Aenean condimentum lectus magna. Suspendisse viverra quam non ante pellentesque, a euismod nunc dapibus. Duis sed congue erat\r\n" +
                    "characteristics: honest, introvert, like criticism, love of Learning, pragmatism\n",
            "firstName: Иванов\n" +
                    "lastName: Иван\n" +
                    "description:\n" +
                    "postId: 762d15a5-3bc9-43ef-ae96-02a680a557d0");
}

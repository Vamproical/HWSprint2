package com.ws.hw1;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Objects;

@AllArgsConstructor
@Getter
@Setter
public class Employee {
    private String firstName;
    private String lastName;
    private String description;
    private List<String> characteristics;
    private String post;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Employee employee = (Employee) o;
        return firstName.equals(employee.firstName) && lastName.equals(employee.lastName) && Objects.equals(description, employee.description) && characteristics.equals(employee.characteristics) && post.equals(employee.post);
    }

    @Override
    public int hashCode() {
        return Objects.hash(firstName, lastName, description, characteristics, post);
    }

    @Override
    public String toString() {
        return "firstName: '" + firstName + "'\n" +
                "lastName: '" + lastName + "'\n"  +
                "description: '" + description + "'\n"  +
                "characteristics=" + characteristics + "\n" +
                "post: '" + post + "'";
    }
}

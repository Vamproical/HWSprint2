package com.ws.hw1.model;

import lombok.*;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
@Entity
public class Employee {
    @Id
    @GeneratedValue
    UUID id;
    String firstName;
    String lastName;

    @Column(length = 1024)
    String description;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "employee_characteristics", joinColumns = @JoinColumn(name = "employee_id"))
    List<String> characteristics;

    @ManyToOne
    @JoinColumn(name = "post_id")
    Post post;

    @Embedded
    Contacts contacts;

    @Enumerated(EnumType.STRING)
    JobType jobType;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Employee employee = (Employee) o;
        return id != null && id.equals(employee.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, post);
    }
}

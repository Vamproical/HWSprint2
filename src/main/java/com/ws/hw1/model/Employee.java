package com.ws.hw1.model;

import lombok.*;
import lombok.extern.jackson.Jacksonized;
import org.hibernate.annotations.GenericGenerator;
import org.testcontainers.shaded.org.apache.commons.lang3.builder.DiffBuilder;
import org.testcontainers.shaded.org.apache.commons.lang3.builder.DiffResult;
import org.testcontainers.shaded.org.apache.commons.lang3.builder.Diffable;
import org.testcontainers.shaded.org.apache.commons.lang3.builder.ToStringStyle;

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
@Jacksonized
@Entity
public class Employee implements Diffable<Employee> {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    UUID id;
    String firstName;
    String lastName;

    @Column(length = 1024)
    String description;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "employee_characteristics", joinColumns = @JoinColumn(name = "employee_id"))
    List<String> characteristics;

    @OneToOne
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
        return id.equals(employee.id)
               && firstName.equals(employee.firstName)
               && lastName.equals(employee.lastName)
               && Objects.equals(description, employee.description)
               && characteristics.equals(employee.characteristics)
               && post.equals(employee.post)
               && contacts.equals(employee.contacts)
               && jobType == employee.jobType;
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }


    @Override
    public DiffResult<Employee> diff(Employee employee) {
        DiffBuilder<Employee> diffBuilder = new DiffBuilder<>(this, employee, ToStringStyle.JSON_STYLE);

        diffBuilder.append("firstName", this.firstName, employee.firstName);
        diffBuilder.append("lastName", this.lastName, employee.lastName);
        diffBuilder.append("description", this.description, employee.description);
        diffBuilder.append("characteristics", this.characteristics, employee.characteristics);
        diffBuilder.append("post", this.post, employee.post);
        diffBuilder.append("contacts", this.contacts, employee.contacts);
        diffBuilder.append("jobType", this.jobType, employee.jobType);

        return diffBuilder.build();
    }
}

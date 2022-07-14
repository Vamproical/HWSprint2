package com.ws.hw1.model;

import lombok.*;
import lombok.extern.jackson.Jacksonized;
import org.hibernate.Hibernate;
import org.hibernate.annotations.GenericGenerator;
import org.testcontainers.shaded.org.apache.commons.lang3.builder.DiffBuilder;
import org.testcontainers.shaded.org.apache.commons.lang3.builder.DiffResult;
import org.testcontainers.shaded.org.apache.commons.lang3.builder.Diffable;
import org.testcontainers.shaded.org.apache.commons.lang3.builder.ToStringStyle;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Objects;
import java.util.UUID;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
@Jacksonized
public class Post implements Diffable<Post> {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    UUID id;
    String name;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Post post = (Post) o;
        return id != null && Objects.equals(id, post.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

    @Override
    public DiffResult<Post> diff(Post post) {
        DiffBuilder<Post> diffBuilder = new DiffBuilder<>(this, post, ToStringStyle.JSON_STYLE);

        diffBuilder.append("name", this.name, post.name);

        return diffBuilder.build();
    }
}

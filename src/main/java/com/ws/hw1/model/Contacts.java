package com.ws.hw1.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.jackson.Jacksonized;
import org.testcontainers.shaded.org.apache.commons.lang3.builder.DiffBuilder;
import org.testcontainers.shaded.org.apache.commons.lang3.builder.DiffResult;
import org.testcontainers.shaded.org.apache.commons.lang3.builder.Diffable;
import org.testcontainers.shaded.org.apache.commons.lang3.builder.ToStringStyle;

import javax.persistence.Embeddable;

@AllArgsConstructor
@Data
@Builder
@Jacksonized
@Embeddable
@NoArgsConstructor
public class Contacts implements Diffable<Contacts> {
    String phone;
    String email;
    String workEmail;

    @Override
    public DiffResult<Contacts> diff(Contacts contacts) {
        DiffBuilder<Contacts> diffBuilder = new DiffBuilder<>(this, contacts, ToStringStyle.JSON_STYLE);

        diffBuilder.append("phone", this.phone, contacts.phone);
        diffBuilder.append("email", this.email, contacts.email);
        diffBuilder.append("workEmail", this.workEmail, contacts.workEmail);

        return diffBuilder.build();
    }
}

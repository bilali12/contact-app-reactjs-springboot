package org.coders.contactapp.domain.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.coders.contactapp.domain.enums.Status;
import org.hibernate.annotations.UuidGenerator;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_DEFAULT;

@Entity
@Data
@NoArgsConstructor @AllArgsConstructor
@JsonInclude(NON_DEFAULT)
@Table(name = "contacts")
public class Contact {
    @Id
    @UuidGenerator
    @Column(unique = true, updatable = false)
    private String id;
    private String fullName;
    private String email;
    private String job;
    private String phone;
    private String address;
    private String status;
    private String imgUrl;
}

package com.cydeo.entity;

import com.cydeo.entity.common.BaseEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.annotations.Where;

@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users")
@Where(clause = "is_deleted=false")
public class User extends BaseEntity {


    @Column(unique = true)
    private String username;   // must be unique
    private String password;
    private String firstname;
    private String lastname;
    private String phone;
    private boolean enabled;

    @ManyToOne
    @JoinColumn(name = "role_id")
    private Role role;  // many-to-one / will be seen under "role_id" column on the "users" table

    @ManyToOne
    private Company company;   // many-to-one / will be seen under "company_id" column on the "users" table


}

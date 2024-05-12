package com.cydeo.entity;

import com.cydeo.entity.common.BaseEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.annotations.SQLRestriction;
import org.hibernate.annotations.Where;

@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users")
//@Where(clause = "is_deleted=false")
@SQLRestriction(value = "is_deleted=false")
public class User extends BaseEntity {


    @Column(unique = true, nullable = false, length = 50)
    private String username;   // must be unique

    @Column(nullable = false)
    private String password;

    @Column(nullable = false, length = 50)
    private String firstname;

    @Column(nullable = false, length = 50)
    private String lastname;

    @Column(nullable = false)
    private String phone;
    private boolean enabled;

    @ManyToOne
    @JoinColumn(name = "role_id", nullable = false)
    private Role role;  // many-to-one / will be seen under "role_id" column on the "users" table

    @ManyToOne(cascade = CascadeType.REMOVE)
    private Company company;   // many-to-one / will be seen under "company_id" column on the "users" table


}

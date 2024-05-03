package com.cydeo.entity;

import com.cydeo.entity.common.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Where;

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
    private Role role;  // many-to-one / will be seen under "role_id" column on the "users" table

    @ManyToOne
    private Company company;   // many-to-one / will be seen under "company_id" column on the "users" table
}

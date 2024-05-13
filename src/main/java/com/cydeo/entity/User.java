package com.cydeo.entity;

import com.cydeo.entity.common.BaseEntity;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.SQLRestriction;

@Getter
@Setter
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
    private Role role;

    @ManyToOne(cascade = CascadeType.REMOVE)
    private Company company;


}

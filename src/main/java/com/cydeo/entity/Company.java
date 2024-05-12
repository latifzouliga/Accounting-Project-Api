package com.cydeo.entity;

import com.cydeo.entity.common.BaseEntity;
import com.cydeo.enums.CompanyStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLRestriction;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "companies")
@SQLRestriction(value = "is_deleted=false")
public class Company extends BaseEntity {

    @Column(unique = true)
    private String title;   //  must be unique
    private String phone;
    private String website;

    @Enumerated(EnumType.STRING)
    private CompanyStatus companyStatus;

    @OneToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private Address address;
}

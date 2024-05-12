package com.cydeo.entity;

import com.cydeo.entity.common.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLRestriction;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "addresses")
@SQLRestriction(value = "is_deleted=false")
//@SQLRestriction("is_deleted <> false")
public class Address extends BaseEntity {
    private String addressLine1;
    private String addressLine2;
    private String city;
    private String state;
    private String country;
    private String zipCode;
}


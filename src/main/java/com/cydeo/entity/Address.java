package com.cydeo.entity;

import com.cydeo.entity.common.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Where;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "addresses")
@Where(clause = "is_deleted=false")
//@SQLRestriction("is_deleted <> false")
public class Address extends BaseEntity {
    private String addressLine1;
    private String addressLine2;
    private String city;
    private String state;
    private String country;
    private String zipCode;
}
// if we create bidirectional relationship, Cascade.PERSIST, Merge doesn't work properly
//    @OneToOne(mappedBy = "address")
//    private Company company;


//    @OneToOne(mappedBy = "address")
//    private ClientVendor clientVendor;

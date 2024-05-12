package com.cydeo.entity;


import com.cydeo.entity.common.BaseEntity;
import com.cydeo.enums.ClientVendorType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLRestriction;
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "clients_vendors")
@SQLRestriction(value = "is_deleted=false")
public class ClientVendor extends BaseEntity {
    private String clientVendorName;
    private String phone;
    private String website;
    @Enumerated(EnumType.STRING)
    private ClientVendorType clientVendorType;
    @ManyToOne
    private Address address;
    @ManyToOne
    private Company company;
}

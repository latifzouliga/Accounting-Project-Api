package com.cydeo.entity;


import com.cydeo.entity.common.BaseEntity;
import com.cydeo.enums.ClientVendorType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Where;

@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "clients_vendors")
@Where(clause = "is_deleted=false")
public class ClientVendor extends BaseEntity {
    private String clientVendorName;
    private String phone;
    private String website;
    @Enumerated(EnumType.STRING)
    private ClientVendorType clientVendorType;
    @ManyToOne
    private Address address; // one-to-one / will be seen under "address_id" column on the "clients_vendors" table
    @ManyToOne
    private Company company; // many-to-one / will be seen under "company_id" column on the "clients_vendors" table
}

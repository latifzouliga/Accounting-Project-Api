package com.cydeo.entity;


import com.cydeo.entity.common.BaseEntity;
import com.cydeo.enums.ClientVendorType;
import jakarta.persistence.*;
import jakarta.validation.Valid;
import lombok.*;
import org.hibernate.annotations.SQLRestriction;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "clients_vendors")
@SQLRestriction(value = "is_deleted=false")
public class ClientVendor extends BaseEntity {

    @Column(nullable = false, length = 50)
    private String clientVendorName;

    @Column(nullable = false)
    private String phone;

    @Column(nullable = false)
    private String website;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private ClientVendorType clientVendorType;

    @OneToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private Address address;

    @ManyToOne(fetch = FetchType.LAZY)
    private Company company;


}

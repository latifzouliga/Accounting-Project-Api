package com.zouliga.entity;

import com.zouliga.entity.common.BaseEntity;
import com.zouliga.enums.InvoiceStatus;
import com.zouliga.enums.InvoiceType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLRestriction;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "invoices")
@SQLRestriction(value = "is_deleted=false")
public class Invoice extends BaseEntity {
    private String invoiceNo;

    @Enumerated(EnumType.STRING)
    private InvoiceStatus invoiceStatus;

    @Enumerated(EnumType.STRING)
    private InvoiceType invoiceType;

    private LocalDate date;

    @ManyToOne
    private ClientVendor clientVendor;

    @ManyToOne
    private Company company;

}

package com.cydeo.entity;

import com.cydeo.entity.common.BaseEntity;
import com.cydeo.enums.InvoiceStatus;
import com.cydeo.enums.InvoiceType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Where;

import java.time.LocalDate;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "invoices")
@Where(clause = "is_deleted=false")
public class Invoice extends BaseEntity {
    private String invoiceNo;

    @Enumerated(EnumType.STRING)
    private InvoiceStatus invoiceStatus ;

    @Enumerated(EnumType.STRING)
    private InvoiceType invoiceType;

    private LocalDate date;

    @ManyToOne
    private ClientVendor clientVendor     ;  // many-to-one / will be seen under "client_vendor_id" column on the "invoices" table

    @ManyToOne
    private Company company               ;   // many-to-one / will be seen under "company_id" column on the "invoices" table

//    @OneToMany(mappedBy = "invoice")
//    private List<InvoiceProduct> invoiceProducts;
}

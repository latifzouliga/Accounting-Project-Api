package com.cydeo.entity;

import com.cydeo.entity.common.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Where;

import java.math.BigDecimal;

@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "invoice_products")
@Where(clause = "is_deleted=false")
public class InvoiceProduct extends BaseEntity {
    private int quantity;
    private BigDecimal price;
    private int tax;
    private BigDecimal profitLoss;
    private int remainingQuantity;
    @ManyToOne(fetch = FetchType.LAZY)
    private Invoice invoice;    // many-to-one / will be seen under "invoice_id" column on the "invoice_products" table
    @ManyToOne(fetch = FetchType.LAZY)
    private Product product;
}

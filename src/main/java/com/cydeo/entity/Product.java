package com.cydeo.entity;

import com.cydeo.entity.common.BaseEntity;
import com.cydeo.enums.ProductUnit;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLRestriction;

@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "products")
@SQLRestriction(value = "is_deleted=false")
public class Product extends BaseEntity {

    @Column(nullable = false)
    private String name;

    private int quantityInStock;
    private int lowLimitAlert;

    @Enumerated(EnumType.STRING)
    private ProductUnit productUnit;

    @ManyToOne(fetch = FetchType.LAZY)
    private Category category;
}

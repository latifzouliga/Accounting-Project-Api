package com.zouliga.entity;

import com.zouliga.entity.common.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.SQLRestriction;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "categories")
@SQLRestriction(value = "is_deleted=false")
public class Category extends BaseEntity {

    private String description;


    @ManyToOne
    private Company company ;     // many-to-one / will be seen under "company" column on the "categories" table
}

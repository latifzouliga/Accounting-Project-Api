package com.zouliga.entity;

import com.zouliga.entity.common.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;
import org.hibernate.annotations.SQLRestriction;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "roles")
@SQLRestriction(value = "is_deleted=false")
public class Role extends BaseEntity {
    String description;
}

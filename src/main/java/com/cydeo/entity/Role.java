package com.cydeo.entity;

import com.cydeo.entity.common.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;
import org.hibernate.annotations.Where;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "roles")
@Where(clause = "is_deleted=false")
public class Role extends BaseEntity {
    String description;
}

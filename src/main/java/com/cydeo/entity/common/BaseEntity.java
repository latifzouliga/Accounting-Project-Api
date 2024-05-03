package com.cydeo.entity.common;

import jakarta.persistence.Column;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import org.springframework.lang.NonNull;

import java.time.LocalDateTime;

@MappedSuperclass
public class BaseEntity {
    @Id
    private Long id;
    @Column(nullable = false, updatable = false)
    private LocalDateTime insertDateTime;
    @Column(nullable = false)
    private LocalDateTime lastInsertedTime;
    @Column(nullable = false, updatable = false)
    private Long insertedUserId;
    @Column(nullable = false)
    private Long lastInsertedUserId;
    private boolean isDeleted;
}

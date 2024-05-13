package com.cydeo.entity.common;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@NoArgsConstructor
@Getter
@Setter
@MappedSuperclass
@EntityListeners({
        BaseEntityListener.class
})
public class BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

//    @CreatedDate
    @Column(nullable = false, updatable = false)
    public LocalDateTime insertDateTime;

//    @LastModifiedDate
    @Column(nullable = false)
    public LocalDateTime lastUpdateDateTime;


    //    @CreatedBy
    @Column(nullable = false)
    public String insertUserId;

    //    @LastModifiedDate
    @Column(nullable = false, updatable = false)
    public String lastUpdateUserId;

    private Boolean isDeleted = false;


}

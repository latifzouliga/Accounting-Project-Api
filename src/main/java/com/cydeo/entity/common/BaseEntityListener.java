package com.cydeo.entity.common;

import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class BaseEntityListener {


    @PrePersist
    @PreUpdate
    public void onPreUpdate(BaseEntity baseEntity) {
        metadata(baseEntity);
    }

    private static void metadata(BaseEntity baseEntity) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        baseEntity.insertDateTime = LocalDateTime.now();
        baseEntity.lastUpdateDateTime = LocalDateTime.now();
        if (authentication != null && authentication.isAuthenticated()) {
            Jwt jwt = (Jwt) authentication.getCredentials();
            String userId = jwt.getClaimAsString("sub");
            baseEntity.insertUserId = userId;
            baseEntity.lastUpdateUserId = userId;
        }
    }



}

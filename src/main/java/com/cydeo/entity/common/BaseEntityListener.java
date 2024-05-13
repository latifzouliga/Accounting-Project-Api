package com.cydeo.entity.common;

import com.cydeo.service.SecurityService;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class BaseEntityListener extends AuditingEntityListener {

    private static SecurityService securityService;

    @Autowired
    public void init(SecurityService securityService) {
        BaseEntityListener.securityService = securityService;
    }

    @PrePersist
    public void onPrePersist(BaseEntity baseEntity) {
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && !authentication.getName().equals("anonymousUser")) {
            baseEntity.insertUserId = securityService.getLoggedInUser().getId();
            baseEntity.lastUpdateUserId = securityService.getLoggedInUser().getId();
        }
    }

    @PreUpdate
    public void onPreUpdate(BaseEntity baseEntity) {
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && !authentication.getName().equals("anonymousUser")) {
            baseEntity.lastUpdateUserId = securityService.getLoggedInUser().getId();
        }
    }

}

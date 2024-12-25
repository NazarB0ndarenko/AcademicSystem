package com.bondarenko.academicsystem.models;

import org.springframework.security.core.GrantedAuthority;

public enum Role implements GrantedAuthority {
    ADMIN, LECTURE, STUDENT;

    @Override
    public String getAuthority() {
        return "ROLE_" + name();
    }
}

package com.bondarenko.academicsystem.models;

import com.bondarenko.academicsystem.dto.LoginDto;
import lombok.AllArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@AllArgsConstructor
public class SecurityUser implements UserDetails {

    private LoginDto loginDto;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(loginDto.getRole());
    }

    @Override
    public String getPassword() {
        return loginDto.getPassword();
    }

    @Override
    public String getUsername() {
        return loginDto.getUsername();
    }
}

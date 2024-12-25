package com.bondarenko.academicsystem.dto;

import com.bondarenko.academicsystem.models.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class LoginDto {
    private String username;
    private String password;
    private Role role;
}

package com.bondarenko.academicsystem.dto.user;

import com.bondarenko.academicsystem.models.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class UserIdRoleDto {
    private long userId;
    private Role role;
}

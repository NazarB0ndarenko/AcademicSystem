package com.bondarenko.academicsystem.enteties;

import com.bondarenko.academicsystem.dto.user.CreateUserDto;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import com.bondarenko.academicsystem.models.Role;

@Entity
@Data
@NoArgsConstructor
@Table(name = "users")
public class User{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "full_name")
    private String fullName;

    @Column(unique = true)
    private String username;

    private String password;

    @Enumerated(EnumType.STRING)
    private Role role;

    public User(CreateUserDto dto) {
        this.fullName = dto.getName();
        this.username = dto.getUsername();

        if (dto.getPassword().isBlank()) {
            this.password = username;
        } else {
            this.password = dto.getPassword();
        }

        switch (dto.getRole()){
            case("admin"): {
                this.role = Role.ADMIN;
                break;
            }
            case("student"): {
                this.role = Role.STUDENT;
                break;
            }
            case("lecture"): {
                this.role = Role.LECTURE;
                break;
            }
            default: {
                throw new IllegalArgumentException("Unknown role");
            }
        }
    }

}


package com.bondarenko.academicsystem.enteties;

import jakarta.persistence.Entity;
import org.springframework.context.annotation.Primary;

@Entity
public class Users {

    @Primary
    Long id;
    String name;
    String password;
}

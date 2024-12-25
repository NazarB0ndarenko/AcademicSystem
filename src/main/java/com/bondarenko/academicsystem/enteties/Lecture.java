package com.bondarenko.academicsystem.enteties;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@NoArgsConstructor
@Table(name = "lectures")
public class Lecture {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @OneToOne(cascade = CascadeType.ALL)
    private User user;

    @OneToMany
    private List<Course> courses;

    @Column(name = "is_active")
    private boolean isActive;

    public Lecture(User user) {
        this.user = user;
        isActive = true;
        this.courses = new ArrayList<>();
    }
}

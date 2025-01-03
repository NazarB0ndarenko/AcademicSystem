package com.bondarenko.academicsystem.enteties;

import com.bondarenko.academicsystem.dto.group.CreateGroupDto;
import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.Collection;

@Data
@Entity
@Table(name = "student_groups")
public class Group {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(unique = true)
    private String name;

    @OneToMany(mappedBy = "group", cascade = CascadeType.ALL, orphanRemoval = true)
    private Collection<Student> students;

    @Column(name = "is_active")
    private boolean isActive;

    public Group(CreateGroupDto dto) {
        this.name = dto.getName();
        this.students = new ArrayList<>();
        this.isActive = true;
    }

    public Group(){
        this.isActive = true;
    }

}

package com.bondarenko.academicsystem.enteties;

import com.bondarenko.academicsystem.dto.CreateGroupDto;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Collection;

@Data
@Entity
@NoArgsConstructor
@Table(name = "student_groups")
public class Group {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(unique = true)
    private String name;

    @OneToMany(mappedBy = "group", cascade = CascadeType.ALL, orphanRemoval = true)
    private Collection<Student> students;

    public Group(CreateGroupDto dto) {
        this.name = dto.getName();
        this.students = new ArrayList<>();
    }

}

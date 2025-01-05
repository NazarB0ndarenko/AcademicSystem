package com.bondarenko.academicsystem.enteties;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collection;

@Entity
@Data
@Table(name = "students")
@NoArgsConstructor
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(cascade = CascadeType.ALL)
    private User user;

    @ManyToOne
    @JoinColumn(name = "group_id")
    private Group group;

    @OneToMany(mappedBy = "student", cascade = CascadeType.ALL, orphanRemoval = true)
    private Collection<Enrolment> enrolments;

    public Student(User user, long groupId) {
        if (groupId > 0) {
            Group group = new Group();
            group.setId(groupId);
            this.group = group;
        } else {
            this.group = null;
        }

        this.user = user;
    }

    public Student(long id) {
        this.id = id;
    }
}

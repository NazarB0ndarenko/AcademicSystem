package com.bondarenko.academicsystem.enteties;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
    @JoinColumn(name = "group_id", nullable = true)
    private Group group;

    @OneToMany
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

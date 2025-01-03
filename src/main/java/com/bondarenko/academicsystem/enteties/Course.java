package com.bondarenko.academicsystem.enteties;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@Table(name = "courses")
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String title;
    private String description;

    @ManyToOne
    @JoinColumn(name = "lecture_id")
    private Lecture lecture;

    public Course(String title, String description, Lecture lecture) {
        this.title = title;
        this.description = description;
        this.lecture = lecture;
    }

    public Course(long id) {
        this.id = id;
    }

}

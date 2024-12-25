package com.bondarenko.academicsystem.enteties;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
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
}

package com.bondarenko.academicsystem.enteties;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Entity
@NoArgsConstructor
@Table(name = "enrolments")
public class Enrolment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    private Student student;

    @ManyToOne
    private Course course;

    private BigDecimal grade;

    public Enrolment(Student student, Course course) {
        this.student = student;
        this.course = course;
        this.grade = BigDecimal.ZERO;
    }

}

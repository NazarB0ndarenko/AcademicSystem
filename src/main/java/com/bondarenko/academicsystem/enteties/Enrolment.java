package com.bondarenko.academicsystem.enteties;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Entity
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

}

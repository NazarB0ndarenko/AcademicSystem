package com.bondarenko.academicsystem.dto.cource;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CourseDto {
    private long id;
    private String description;

    public CourseDto(long id, String title, String lectureName) {
        this.id = id;
        this.description = title + " (Lecture: " + lectureName + ")";
    }
}

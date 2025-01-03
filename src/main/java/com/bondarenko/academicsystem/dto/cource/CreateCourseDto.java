package com.bondarenko.academicsystem.dto.cource;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class CreateCourseDto {
    private String title;
    private String description;
    private long lectureId;
}

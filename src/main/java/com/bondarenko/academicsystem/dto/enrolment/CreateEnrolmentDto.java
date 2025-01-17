package com.bondarenko.academicsystem.dto.enrolment;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class CreateEnrolmentDto {
    private long studentGroupId;
    private long courseId;
}

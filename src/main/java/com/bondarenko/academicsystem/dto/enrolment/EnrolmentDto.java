package com.bondarenko.academicsystem.dto.enrolment;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
public class EnrolmentDto {
    private Long studentId;
    private String studentFullName;
    private BigDecimal grade;

}

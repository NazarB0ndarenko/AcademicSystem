package com.bondarenko.academicsystem.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Map;

@Getter
@Setter
@AllArgsConstructor
public class AssignGradesDto {
    @NotNull
    private long courseId;
    @NotNull
    private Map<Long, BigDecimal> grades;
}

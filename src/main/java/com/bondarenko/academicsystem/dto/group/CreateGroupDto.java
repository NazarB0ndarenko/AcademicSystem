package com.bondarenko.academicsystem.dto.group;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class CreateGroupDto {
    String name;
    List<Long> studentIds;
}

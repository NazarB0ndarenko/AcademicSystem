package com.bondarenko.academicsystem.controllers;

import com.bondarenko.academicsystem.dto.NameIdDto;
import com.bondarenko.academicsystem.dto.enrolment.EnrolmentsInfoDto;
import com.bondarenko.academicsystem.services.EnrolmentService;
import com.bondarenko.academicsystem.services.StudentService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/student")
@AllArgsConstructor
public class StudentController {
    private final EnrolmentService enrolmentService;
    private final StudentService studentService;

    @GetMapping
    public String checkGrades(Model model) {

        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        NameIdDto nameIdDto = studentService.findNameIdByUsername(username);
        List<EnrolmentsInfoDto> enrolments = enrolmentService.getEnrolmentByStudentId(nameIdDto.getId());

        model.addAttribute("enrolments", enrolments);
        model.addAttribute("studentName", nameIdDto.getName());

        return "StudentDashboard";
    }
}

package com.bondarenko.academicsystem.controllers.admin;

import com.bondarenko.academicsystem.dto.enrolment.CreateEnrolmentDto;
import com.bondarenko.academicsystem.dto.cource.CourseDto;
import com.bondarenko.academicsystem.dto.group.GroupDto;
import com.bondarenko.academicsystem.services.CourseService;
import com.bondarenko.academicsystem.services.EnrolmentService;
import com.bondarenko.academicsystem.services.GroupService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/admin/create-enrolment")
@AllArgsConstructor
public class AddEnrolmentController {
    private final CourseService courseService;
    private final GroupService groupService;
    private final EnrolmentService enrolmentService;

    @GetMapping
    public String addEnrolment(Model model) {
        List<GroupDto> groups = groupService.getAllGroups();
        List<CourseDto> courses = courseService.getAllCourses();

        model.addAttribute("groups", groups);
        model.addAttribute("courses", courses);
        return "AddEnrolment";
    }
    
    @PostMapping
    public String addEnrolment(@ModelAttribute CreateEnrolmentDto dto) {
        enrolmentService.createEnrolments(dto);

        return "redirect:/admin/create-enrolment";
    }
}

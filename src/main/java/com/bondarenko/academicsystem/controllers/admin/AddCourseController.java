package com.bondarenko.academicsystem.controllers.admin;

import com.bondarenko.academicsystem.dto.cource.CreateCourseDto;
import com.bondarenko.academicsystem.dto.LectureNameIdDto;
import com.bondarenko.academicsystem.services.CourseService;
import com.bondarenko.academicsystem.services.LectureService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Slf4j
@Controller
@AllArgsConstructor
@RequestMapping("/admin/create-course")
public class AddCourseController {

    private final LectureService lectureService;
    private final CourseService courseService;

    @GetMapping
    public String addCourse(Model model) {
        List<LectureNameIdDto> lectures = lectureService.getAllLectures();

        model.addAttribute("lectures", lectures);
        return "AddCourse";
    }

    @PostMapping
    public String addCourseSubmit(@ModelAttribute CreateCourseDto dto) {
        courseService.addCourse(dto);
        return "redirect:/admin/create-course";
    }
}

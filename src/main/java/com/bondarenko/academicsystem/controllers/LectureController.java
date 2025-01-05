package com.bondarenko.academicsystem.controllers;

import com.bondarenko.academicsystem.dto.AssignGradesDto;
import com.bondarenko.academicsystem.dto.cource.CourseDto;
import com.bondarenko.academicsystem.dto.enrolment.EnrolmentDto;
import com.bondarenko.academicsystem.services.CourseService;
import com.bondarenko.academicsystem.services.EnrolmentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.stereotype.Controller;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@Slf4j
@Controller
@RequestMapping("/lecture")
public class LectureController {

    @Autowired
    private CourseService courseService;

    @Autowired
    private EnrolmentService enrolmentService;

    @GetMapping("/courses")
    public String getCourses(Model model) {
        List<CourseDto> courses = courseService.getCoursesForLecturer();
        model.addAttribute("courses", courses);
        return "LectureDashboard";
    }

    @GetMapping("/courses/{courseId}/enrolments")
    @ResponseBody
    public List<EnrolmentDto> getEnrolmentsByCourse(@PathVariable Long courseId) {
        return enrolmentService.getEnrolmentsByCourseId(courseId);
    }

    @PostMapping("/assign-grades")
    public String assignGrades(@RequestBody AssignGradesDto assignGradesDto) {

        Map<Long, BigDecimal> grades = assignGradesDto.getGrades();
        long courseId = assignGradesDto.getCourseId();

        log.info("Assigning grades for {}", courseId);
        log.info("Grades for {}", grades);

        grades.forEach((studentId, grade) -> enrolmentService.updateGrade(courseId, studentId, grade));

        return "redirect:/lecture/courses";
    }
}


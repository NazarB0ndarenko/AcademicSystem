package com.bondarenko.academicsystem.services;

import com.bondarenko.academicsystem.dto.cource.CourseDto;
import com.bondarenko.academicsystem.dto.cource.CreateCourseDto;
import com.bondarenko.academicsystem.enteties.Course;
import com.bondarenko.academicsystem.enteties.Lecture;
import com.bondarenko.academicsystem.repositories.CourseRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@Transactional
@AllArgsConstructor
public class CourseService {

    private final CourseRepository courseRepository;

    public void addCourse(CreateCourseDto dto) {
        log.info("Adding course {}", dto);

        Lecture lecture = new Lecture();
        lecture.setId(dto.getLectureId());

        Course course = new Course(dto.getTitle(), dto.getDescription(), lecture);
        courseRepository.save(course);

        log.info("Course {} added", dto);
    }

    public List<CourseDto> getAllCourses() {
        log.info("Getting all courses");

        return courseRepository.getAllCourses();
    }


}

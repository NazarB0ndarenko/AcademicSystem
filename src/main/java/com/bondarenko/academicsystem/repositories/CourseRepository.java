package com.bondarenko.academicsystem.repositories;

import com.bondarenko.academicsystem.dto.cource.CourseDto;
import com.bondarenko.academicsystem.enteties.Course;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CourseRepository extends CrudRepository<Course, Long> {

    @Query("SELECT new com.bondarenko.academicsystem.dto.cource.CourseDto(c.id, c.title, c.lecture.user.fullName) " +
            "FROM Course c")
    List<CourseDto> getAllCourses();

    @Query("SELECT new com.bondarenko.academicsystem.dto.cource.CourseDto(c.id, c.title, c.lecture.user.fullName) " +
            "FROM Course c " +
            "WHERE c.lecture.id = :id")
    List<CourseDto> getAllCoursesForLecture(@Param("id") long id);
}

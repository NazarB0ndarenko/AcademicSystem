package com.bondarenko.academicsystem.repositories;

import com.bondarenko.academicsystem.enteties.Course;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CourseRepository extends CrudRepository<Course, Long> {
}

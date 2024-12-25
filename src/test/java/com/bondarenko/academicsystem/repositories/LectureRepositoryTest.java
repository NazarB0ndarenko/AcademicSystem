package com.bondarenko.academicsystem.repositories;

import com.bondarenko.academicsystem.enteties.Lecture;
import com.bondarenko.academicsystem.enteties.User;
import com.bondarenko.academicsystem.models.Role;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class LectureRepositoryTest {

    @Autowired
    private LectureRepository lectureRepository;

    private Lecture lecture;

    @BeforeEach
    void setUp() {
        User user = new User();
        user.setPassword("password");
        user.setUsername("username");
        user.setFullName("fullName");
        user.setRole(Role.LECTURE);
        lecture = new Lecture(user);

        lectureRepository.save(lecture);
    }

    @Test
    void deactivateLecture() {
        boolean rowsUpdated = lectureRepository.deactivateLecture(lecture.getId());

        assertTrue(rowsUpdated, "One row should be updated");

        Lecture updatedLecture = lectureRepository.findById(lecture.getId()).orElseThrow();
        assertFalse(updatedLecture.isActive(), "Lecture should be deactivated");
    }
}
package com.bondarenko.academicsystem.repositories;

import com.bondarenko.academicsystem.dto.LectureNameIdDto;
import com.bondarenko.academicsystem.enteties.Lecture;
import com.bondarenko.academicsystem.enteties.User;
import com.bondarenko.academicsystem.models.Role;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

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

        lecture = lectureRepository.save(lecture);
    }

    @Test
    void deactivateLecture() {
        lectureRepository.deactivateLecture(lecture.getId());

        Lecture updatedLecture = lectureRepository.findById(lecture.getId()).orElseThrow();
        assertFalse(updatedLecture.isActive(), "Lecture should be deactivated");
    }

    @Test
    void getAllLectures() {
        List<LectureNameIdDto> lectures = lectureRepository.getAllLectureNameIdDto();

        assertEquals(lectures.get(0).getId(), lecture.getId());
        assertEquals(lectures.get(0).getFullName(), lecture.getUser().getFullName());
    }
}
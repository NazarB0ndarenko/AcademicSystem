package com.bondarenko.academicsystem.services;

import com.bondarenko.academicsystem.enteties.Lecture;
import com.bondarenko.academicsystem.enteties.User;
import com.bondarenko.academicsystem.repositories.LectureRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@AllArgsConstructor
public class LectureService {

    private final LectureRepository lectureRepository;

    public Lecture addLecture(User user) {
        Lecture lecture = new Lecture(user);

        log.info("Adding new lecture for user with username: {}", user.getUsername());

        lectureRepository.save(lecture);

        log.info("Successfully added new lecture for user with username: {}", user.getUsername());

        return lecture;
    }

    public void deleteLecture(long userId) {
        log.info("Disabling lecture for user with id: {}", userId);

        lectureRepository.deactivateLecture(userId);

        log.info("Successfully disabled lecture for user with id: {}", userId);
    }
}

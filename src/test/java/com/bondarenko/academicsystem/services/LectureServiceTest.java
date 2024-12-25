package com.bondarenko.academicsystem.services;

import static org.junit.jupiter.api.Assertions.*;

import com.bondarenko.academicsystem.enteties.Lecture;
import com.bondarenko.academicsystem.enteties.User;
import com.bondarenko.academicsystem.repositories.LectureRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.*;

class LectureServiceTest {

    @Mock
    private LectureRepository lectureRepository;

    @InjectMocks
    private LectureService lectureService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void addLecture_Success() {
        User mockUser = new User();
        mockUser.setUsername("testUser");

        Lecture mockLecture = new Lecture(mockUser);
        when(lectureRepository.save(any(Lecture.class))).thenReturn(mockLecture);

        Lecture addedLecture = lectureService.addLecture(mockUser);

        assertNotNull(addedLecture);
        assertEquals(mockUser.getUsername(), addedLecture.getUser().getUsername());
        verify(lectureRepository, times(1)).save(any(Lecture.class));
    }

    @Test
    void deleteLecture_Success() {
        long userId = 1L;

        when(lectureRepository.deactivateLecture(userId)).thenReturn(true);

        lectureService.deleteLecture(userId);

        verify(lectureRepository, times(1)).deactivateLecture(userId);
    }
}

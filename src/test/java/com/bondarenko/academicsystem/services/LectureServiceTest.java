package com.bondarenko.academicsystem.services;

import static org.junit.jupiter.api.Assertions.*;

import com.bondarenko.academicsystem.dto.LectureNameIdDto;
import com.bondarenko.academicsystem.enteties.Lecture;
import com.bondarenko.academicsystem.enteties.User;
import com.bondarenko.academicsystem.repositories.LectureRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

import static org.mockito.Mockito.*;

class LectureServiceTest {

    @Mock
    private LectureRepository lectureRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

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

        when(passwordEncoder.encode(mockUser.getPassword())).thenReturn("encodedPassword");

        Lecture addedLecture = lectureService.addLecture(mockUser);

        assertNotNull(addedLecture);
        assertEquals(mockUser.getUsername(), addedLecture.getUser().getUsername());
        verify(lectureRepository, times(1)).save(any(Lecture.class));
    }

    @Test
    void deleteLecture_Success() {
        long userId = 1L;

        doNothing().when(lectureRepository).deactivateLecture(userId);

        lectureService.deleteLecture(userId);

        verify(lectureRepository, times(1)).deactivateLecture(userId);
    }


    @Test
    void testGetAllLectures() {
        LectureNameIdDto mockDto = new LectureNameIdDto(11, "Test Lecture");
        List<LectureNameIdDto> mockResponse = List.of(mockDto);

        when(lectureRepository.getAllLectureNameIdDto()).thenReturn(mockResponse);

        List<LectureNameIdDto> result = lectureService.getAllLectures();

        verify(lectureRepository, times(1)).getAllLectureNameIdDto();
        assertNotNull(result, "The result should not be null");
        assertEquals(1, result.size(), "The result size should be 1");
        assertEquals(mockDto, result.get(0), "The result should match the mock DTO");
    }
}

package com.bondarenko.academicsystem.services;

import com.bondarenko.academicsystem.dto.StudentNameIdDto;
import com.bondarenko.academicsystem.enteties.Student;
import com.bondarenko.academicsystem.enteties.User;
import com.bondarenko.academicsystem.repositories.StudentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class StudentServiceTest {

    @Mock
    private StudentRepository studentRepository;

    @InjectMocks
    private StudentService studentService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void saveStudent_Success() {
        User mockUser = new User();
        mockUser.setUsername("testUser");

        long groupInput = 1L;
        Student mockStudent = new Student(mockUser, groupInput);
        when(studentRepository.save(any(Student.class))).thenReturn(mockStudent);

        Student savedStudent = studentService.saveStudent(mockUser, groupInput);

        assertNotNull(savedStudent);
        assertEquals(mockUser.getUsername(), savedStudent.getUser().getUsername());
        verify(studentRepository, times(1)).save(any(Student.class));
    }

    @Test
    void findStudentsWithoutGroup_Success() {
        StudentNameIdDto studentWithoutGroup = new StudentNameIdDto(1L, "Student Without Group");
        when(studentRepository.findStudentsWithoutGroup()).thenReturn(List.of(studentWithoutGroup));

        List<StudentNameIdDto> result = studentService.findStudentsWithoutGroup();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Student Without Group", result.get(0).getFullName());
        verify(studentRepository, times(1)).findStudentsWithoutGroup();
    }

    @Test
    void deleteStudent_WithGroup() {
        Long studentId = 1L;
        when(studentRepository.getGroupId(studentId)).thenReturn(Optional.of(1L));

        studentService.deleteStudent(studentId);

        verify(studentRepository, times(1)).getGroupId(studentId);
        verify(studentRepository, times(1)).removeStudentFromGroup(studentId);
        verify(studentRepository, times(1)).deleteById(studentId);
    }

    @Test
    void deleteStudent_WithoutGroup() {
        Long studentId = 2L;
        when(studentRepository.getGroupId(studentId)).thenReturn(Optional.empty());

        studentService.deleteStudent(studentId);

        verify(studentRepository, times(1)).getGroupId(studentId);
        verify(studentRepository, never()).removeStudentFromGroup(studentId);
        verify(studentRepository, times(1)).deleteById(studentId);
    }

    @Test
    void removeStudentFromGroup_Success() {
        long studentId = 1L;

        studentService.removeStudentFromGroup(studentId);

        verify(studentRepository, times(1)).removeStudentFromGroup(studentId);
    }
}

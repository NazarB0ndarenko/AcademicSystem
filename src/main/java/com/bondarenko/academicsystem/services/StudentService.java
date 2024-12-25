package com.bondarenko.academicsystem.services;

import com.bondarenko.academicsystem.dto.StudentNameIdDto;
import com.bondarenko.academicsystem.enteties.Student;
import com.bondarenko.academicsystem.enteties.User;
import com.bondarenko.academicsystem.repositories.StudentRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@AllArgsConstructor
@Transactional
public class StudentService {

    private final StudentRepository studentRepository;

    public Student saveStudent(User user, long groupInput) {
        log.info("Creating student with username: {}", user.getUsername());

        Student student = new Student(user, groupInput);
        studentRepository.save(student);

        log.info("Successfully created student with username: {}", student.getUser().getUsername());
        return student;
    }

    public List<StudentNameIdDto> findStudentsWithoutGroup() {
        log.info("Finding students without group");
        return studentRepository.findStudentsWithoutGroup();
    }

    public void deleteStudent(Long studentId) {
        log.info("Deleting student with id: {}", studentId);

        Optional<Long> groupId = studentRepository.getGroupId(studentId);

        if (groupId.isPresent()) {
            studentRepository.removeStudentFromGroup(studentId);
        }

        studentRepository.deleteById(studentId);

        log.info("Successfully deleted student with id: {}", studentId);
    }

    public void removeStudentFromGroup(long studentId) {
        log.info("Removing from group student with id: {}", studentId);

        studentRepository.removeStudentFromGroup(studentId);

        log.info("Removing from group student with id: {}", studentId);
    }

}

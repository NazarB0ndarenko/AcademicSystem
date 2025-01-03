package com.bondarenko.academicsystem.services;

import com.bondarenko.academicsystem.dto.CreateEnrolmentDto;
import com.bondarenko.academicsystem.enteties.Course;
import com.bondarenko.academicsystem.enteties.Enrolment;
import com.bondarenko.academicsystem.enteties.Group;
import com.bondarenko.academicsystem.enteties.Student;
import com.bondarenko.academicsystem.repositories.EnrolmentRepository;
import com.bondarenko.academicsystem.repositories.GroupRepository;
import com.bondarenko.academicsystem.repositories.StudentRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@AllArgsConstructor
public class EnrolmentService {

    private final EnrolmentRepository enrolmentRepository;
    private final StudentRepository studentRepository;

    public List<Enrolment> createEnrolments(CreateEnrolmentDto dto){
        List<Long> students = studentRepository.findStudentIdsByGroupId(dto.getStudentGroupId());
        Course course = new Course(dto.getCourseId());

        List<Enrolment> enrolments = new ArrayList<>();

        log.info("Enrolling students from group {} to the course with id {}", dto.getCourseId(), dto.getCourseId());

        for(Long studentId : students){
            Student student = new Student(studentId);

            enrolments.add(new Enrolment(student, course));
        }

        enrolmentRepository.saveAll(enrolments);
        log.info("Students enrolled to the course with id: {}", dto.getCourseId());

        return enrolments;
    }
}

package com.bondarenko.academicsystem.repositories;

import com.bondarenko.academicsystem.dto.enrolment.EnrolmentDto;
import com.bondarenko.academicsystem.dto.enrolment.EnrolmentsInfoDto;
import com.bondarenko.academicsystem.enteties.Enrolment;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface EnrolmentRepository extends CrudRepository<Enrolment, Long> {

    @Query("SELECT new com.bondarenko.academicsystem.dto.enrolment.EnrolmentDto(e.student.id, e.student.user.fullName, e.grade) " +
            "FROM Enrolment e " +
            "WHERE e.course.id = :courseId")
    List<EnrolmentDto> getEnrolmentByCourse_Id(@Param("courseId") long courseId);

    @Modifying
    @Query("UPDATE Enrolment e " +
            "SET e.grade = :grade " +
            "WHERE e.student.id =:studentId AND e.course.id = :courseId")
    void updateGrade(@Param("courseId") long courseId,
                     @Param("studentId") long studentId,
                     @Param("grade") BigDecimal grade);

    @Query("SELECT new com.bondarenko.academicsystem.dto.enrolment.EnrolmentsInfoDto(e.course.title, e.course.lecture.user.fullName, e.grade) " +
            "FROM Enrolment e " +
            "WHERE e.student.id = :studentId")
    List<EnrolmentsInfoDto> getEnrolmentForStudent(@Param("studentId") long studentId);
}

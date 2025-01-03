package com.bondarenko.academicsystem.repositories;

import com.bondarenko.academicsystem.dto.StudentNameIdDto;
import com.bondarenko.academicsystem.enteties.Group;
import com.bondarenko.academicsystem.enteties.Student;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Optional;

@Repository
public interface StudentRepository extends CrudRepository<Student, Long> {

    @Query("SELECT new com.bondarenko.academicsystem.dto.StudentNameIdDto(s.id, s.user.fullName) FROM Student s WHERE s.group IS NULL")
    List<StudentNameIdDto> findStudentsWithoutGroup();

    void deleteById(Long id);

    @Query("SELECT s.group.id " +
            "FROM Student s " +
            "WHERE s.id = :id")
    public Optional<Long> getGroupId(@RequestParam Long id);

    @Modifying
    @Transactional
    @Query("UPDATE Student s SET s.group = null WHERE s.id = :studentId")
    void removeStudentFromGroup(@Param("studentId") Long studentId);

    @Modifying
    @Transactional
    @Query("UPDATE Student s SET s.group = :group WHERE s.id = :studentId")
    void addStudentToGroup(@Param("studentId") Long studentId, @Param("group") Group group);

    @Query("SELECT s.id FROM Student s WHERE s.group.id = :groupId")
    List<Long> findStudentIdsByGroupId(@Param("groupId") Long groupId);

}

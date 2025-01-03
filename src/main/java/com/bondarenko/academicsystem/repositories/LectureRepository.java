package com.bondarenko.academicsystem.repositories;

import com.bondarenko.academicsystem.dto.LectureNameIdDto;
import com.bondarenko.academicsystem.enteties.Lecture;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface LectureRepository extends CrudRepository<Lecture, Long> {

    @Modifying(clearAutomatically = true)
    @Transactional
    @Query("UPDATE Lecture l " +
            "SET l.isActive = false " +
            "WHERE l.id = :id")
    void deactivateLecture(@Param("id") Long id);

    @Query("SELECT new com.bondarenko.academicsystem.dto.LectureNameIdDto(l.id, l.user.fullName) " +
            "FROM Lecture l")
    List<LectureNameIdDto> getAllLectureNameIdDto();
}

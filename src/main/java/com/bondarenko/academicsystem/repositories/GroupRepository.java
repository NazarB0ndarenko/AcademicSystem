package com.bondarenko.academicsystem.repositories;

import com.bondarenko.academicsystem.dto.GroupDto;
import com.bondarenko.academicsystem.enteties.Group;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;

@Repository
public interface GroupRepository extends JpaRepository<Group, Long> {

    @Query("SELECT new com.bondarenko.academicsystem.dto.GroupDto(g.id, g.name) FROM Group g")
    List<GroupDto> findAllGroupDtos();

    void deleteGroupById(Long id);
}

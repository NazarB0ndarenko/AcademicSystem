package com.bondarenko.academicsystem.repositories;

import com.bondarenko.academicsystem.dto.GroupDto;
import com.bondarenko.academicsystem.enteties.Group;
import com.bondarenko.academicsystem.enteties.Student;
import com.bondarenko.academicsystem.enteties.User;
import com.bondarenko.academicsystem.models.Role;
import org.hibernate.validator.internal.constraintvalidators.bv.AssertFalseValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;


import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class GroupRepositoryTest {

    @Autowired
    private GroupRepository groupRepository;

    @Autowired
    private StudentRepository studentRepository;

    private Group group;
    private Student student1;
    private Student student2;


    @BeforeEach
    void setUp() {

        User user1 = new User();
        user1.setFullName("Nazar");
        user1.setUsername("user1");
        user1.setPassword("password");
        user1.setRole(Role.STUDENT);

        User user2 = new User();
        user2.setFullName("Ivan");
        user2.setUsername("user2");
        user2.setPassword("password");
        user2.setRole(Role.STUDENT);

        group = new Group();
        group.setName("Test Group");
        group = groupRepository.save(group);

        student1 = new Student();
        student1.setUser(user1);

        student2 = new Student();
        student2.setUser(user2);

        student1.setGroup(group);
        student2.setGroup(group);

        studentRepository.saveAll(List.of(student1, student2));
    }

    @Test
    void testFindAllGroupDtos() {
        List<GroupDto> groupDtos = groupRepository.findAllGroupDtos();

        assertFalse(groupDtos.isEmpty(), "Group DTOs list should not be empty");
        assertEquals(1, groupDtos.size(), "There should be one group in the list");
        assertEquals(group.getName(), groupDtos.get(0).getName(), "Group name should match");
        assertEquals(group.getId(), groupDtos.get(0).getId(), "Group ID should match");
    }
}

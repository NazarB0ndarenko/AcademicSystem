package com.bondarenko.academicsystem.repositories;

import static org.junit.jupiter.api.Assertions.*;

import com.bondarenko.academicsystem.dto.StudentNameIdDto;
import com.bondarenko.academicsystem.enteties.Group;
import com.bondarenko.academicsystem.enteties.Student;
import com.bondarenko.academicsystem.enteties.User;
import com.bondarenko.academicsystem.models.Role;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@DataJpaTest
@Transactional
class StudentRepositoryTest {

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private GroupRepository groupRepository;

    @Autowired
    private EntityManager entityManager;

    private static final String STUDENT_WITHOUT_GROUP_NAME = "Student Without Group";
    private static final String STUDENT_WITHOUT_GROUP_USERNAME = "studentWithoutGroup";
    private static final String STUDENT_WITH_GROUP_NAME = "Student With Group";
    private static final String STUDENT_WITH_GROUP_USERNAME = "studentWithGroup";
    private static final String TEST_GROUP_NAME = "Test Group";

    private Student studentWithGroup;
    private Student studentWithoutGroup;
    private Group group;

    @BeforeEach
    void setUp() {
        User userWithoutGroup = new User();
        userWithoutGroup.setFullName(STUDENT_WITHOUT_GROUP_NAME);
        userWithoutGroup.setUsername(STUDENT_WITHOUT_GROUP_USERNAME);
        userWithoutGroup.setPassword("password");
        userWithoutGroup.setRole(Role.STUDENT);

        studentWithoutGroup = new Student();
        studentWithoutGroup.setUser(userWithoutGroup);
        studentRepository.save(studentWithoutGroup);

        User userWithGroup = new User();
        userWithGroup.setFullName(STUDENT_WITH_GROUP_NAME);
        userWithGroup.setUsername(STUDENT_WITH_GROUP_USERNAME);
        userWithGroup.setPassword("password");
        userWithGroup.setRole(Role.STUDENT);

        group = new Group();
        group.setName(TEST_GROUP_NAME);
        group = groupRepository.save(group);

        studentWithGroup = new Student();
        studentWithGroup.setUser(userWithGroup);
        studentWithGroup.setGroup(group);
        studentRepository.save(studentWithGroup);
    }

    @Test
    void findStudentsWithoutGroup() {
        List<StudentNameIdDto> studentsWithoutGroup = studentRepository.findStudentsWithoutGroup();

        assertAll(
                () -> assertEquals(1, studentsWithoutGroup.size(), "Should find one student without group"),
                () -> assertEquals(studentWithoutGroup.getId(), studentsWithoutGroup.get(0).getId()),
                () -> assertEquals(STUDENT_WITHOUT_GROUP_NAME, studentsWithoutGroup.get(0).getFullName())
        );
    }

    @Test
    void deleteById() {
        studentRepository.deleteById(studentWithoutGroup.getId());
        Optional<Student> deletedStudent = studentRepository.findById(studentWithoutGroup.getId());

        assertFalse(deletedStudent.isPresent(), "Deleted student should not be found");
    }

    @Test
    void getGroupId() {
        Optional<Long> groupId = studentRepository.getGroupId(studentWithGroup.getId());

        assertAll(
                () -> assertTrue(groupId.isPresent(), "Group ID should be present for student with group"),
                () -> assertEquals(group.getId(), groupId.get(), "Group ID should match")
        );
    }

    @Test
    void getGroupIdForStudentWithoutGroup() {
        Optional<Long> groupId = studentRepository.getGroupId(studentWithoutGroup.getId());

        assertFalse(groupId.isPresent(), "Group ID should not be present for student without group");
    }

    @Test
    void testRemoveStudentFromGroup() {
        studentRepository.removeStudentFromGroup(studentWithGroup.getId());

        entityManager.clear();

        Student updatedStudent = studentRepository.findById(studentWithGroup.getId())
                .orElseThrow(() -> new AssertionError("Student should exist"));
        entityManager.refresh(updatedStudent);

        assertNull(updatedStudent.getGroup(), "Student's group should be null after removal");

        Group updatedGroup = groupRepository.findById(group.getId())
                .orElseThrow(() -> new AssertionError("Group should exist"));

        List<Student> students = new ArrayList<>(updatedGroup.getStudents());

        assertTrue(students.isEmpty(), "Group's students should be empty after removal");
    }

    @Test
    void testAddStudentToGroup() {
        assertNull(studentWithoutGroup.getGroup(), "Student without group should initially have no group");

        studentRepository.addStudentToGroup(studentWithoutGroup.getId(), group);

        entityManager.clear();

        Student updatedStudent = studentRepository.findById(studentWithoutGroup.getId())
                .orElseThrow(() -> new AssertionError("Student should exist"));

        assertAll(
                () -> assertNotNull(updatedStudent.getGroup(), "Student's group should not be null after adding"),
                () -> assertEquals(group.getId(), updatedStudent.getGroup().getId(), "Student's group ID should match")
        );

        Group updatedGroup = groupRepository.findById(group.getId())
                .orElseThrow(() -> new AssertionError("Group should exist"));

        assertTrue(
                updatedGroup.getStudents().contains(updatedStudent),
                "Group should contain the student after addition"
        );
    }




}


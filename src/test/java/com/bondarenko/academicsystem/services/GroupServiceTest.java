package com.bondarenko.academicsystem.services;

import static org.junit.jupiter.api.Assertions.*;

import com.bondarenko.academicsystem.dto.CreateGroupDto;
import com.bondarenko.academicsystem.dto.GroupDto;
import com.bondarenko.academicsystem.enteties.Group;
import com.bondarenko.academicsystem.enteties.Student;
import com.bondarenko.academicsystem.repositories.GroupRepository;
import com.bondarenko.academicsystem.repositories.StudentRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;


import static org.mockito.Mockito.*;

@Slf4j
class GroupServiceTest {

    @Mock
    private GroupRepository groupRepository;

    @Mock
    private StudentRepository studentRepository;

    @InjectMocks
    private GroupService groupService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getAllGroups_Success() {
        GroupDto groupDto1 = new GroupDto(1L, "Group 1");
        GroupDto groupDto2 = new GroupDto(2L, "Group 2");
        when(groupRepository.findAllGroupDtos()).thenReturn(List.of(groupDto1, groupDto2));

        List<GroupDto> groups = groupService.getAllGroups();

        assertNotNull(groups);
        assertEquals(2, groups.size());
        assertEquals("Group 1", groups.get(0).getName());
        assertEquals("Group 2", groups.get(1).getName());
        verify(groupRepository, times(1)).findAllGroupDtos();
    }

    @Test
    void saveGroupWithoutStudents_Success() {
        CreateGroupDto createGroupDto = new CreateGroupDto("Test Group Without Students", null);

        Group group = new Group(createGroupDto);
        group.setName(createGroupDto.getName());

        when(groupRepository.save(any(Group.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Group savedGroup = groupService.saveGroup(createGroupDto);

        assertNotNull(savedGroup);
        assertEquals("Test Group Without Students", savedGroup.getName());
        verify(groupRepository, times(1)).save(any(Group.class));
        verify(studentRepository, times(0)).addStudentToGroup(anyLong(), any(Group.class));
    }

    @Test
    void saveGroupWithStudents_Success() {
        CreateGroupDto createGroupDto = new CreateGroupDto("Test Group With Students", List.of(1L, 2L));

        Group group = new Group(createGroupDto);
        group.setName(createGroupDto.getName());

        Student student1 = new Student();
        student1.setId(1L);

        Student student2 = new Student();
        student2.setId(2L);

        when(groupRepository.save(any(Group.class))).thenAnswer(invocation -> invocation.getArgument(0));

        doNothing().when(studentRepository).addStudentToGroup(1L, group);
        doNothing().when(studentRepository).addStudentToGroup(2L, group);

        Group savedGroup = groupService.saveGroup(createGroupDto);

        assertNotNull(savedGroup);
        assertEquals("Test Group With Students", savedGroup.getName());
        verify(groupRepository, times(1)).save(any(Group.class));
        verify(studentRepository, times(1)).addStudentToGroup(1L, group);
        verify(studentRepository, times(1)).addStudentToGroup(2L, group);
    }
}



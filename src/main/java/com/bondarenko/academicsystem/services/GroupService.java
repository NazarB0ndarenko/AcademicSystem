package com.bondarenko.academicsystem.services;

import com.bondarenko.academicsystem.dto.group.CreateGroupDto;
import com.bondarenko.academicsystem.dto.group.GroupDto;
import com.bondarenko.academicsystem.enteties.Group;
import com.bondarenko.academicsystem.repositories.GroupRepository;
import com.bondarenko.academicsystem.repositories.StudentRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@Transactional
@AllArgsConstructor
public class GroupService {

    private final GroupRepository groupRepository;
    private final StudentRepository studentRepository;

    public List<GroupDto> getAllGroups() {
        log.info("Fetching all groups");

        List<GroupDto> groups = groupRepository.findAllGroupDtos();

        log.info("Fetched {} groups", groups.size());

        return groups;
    }

    public Group saveGroup(CreateGroupDto createGroupDto) {
        log.info("Saving group with name: {}", createGroupDto.getName());

        Group group = new Group(createGroupDto);
        groupRepository.save(group);

        if (createGroupDto.getStudentIds() != null) {
            createGroupDto.getStudentIds().forEach(studentId -> studentRepository.addStudentToGroup(studentId, group));
        }

        log.info("Successfully saved group with name: {}", group.getName());
        return group;
    }

    public void deleteGroup(long groupId) {
        log.info("Deleting group with id: {}", groupId);

        groupRepository.deactivateGroup(groupId);

        log.info("Successfully deleted group with id: {}", groupId);
    }

    public boolean groupNameExists(String name) {
        return groupRepository.existsByName(name);
    }


}


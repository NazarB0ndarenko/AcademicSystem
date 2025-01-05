package com.bondarenko.academicsystem.controllers.admin;

import com.bondarenko.academicsystem.dto.user.CreateUserDto;
import com.bondarenko.academicsystem.dto.group.GroupDto;
import com.bondarenko.academicsystem.enteties.User;
import com.bondarenko.academicsystem.services.CustomUserDetailsService;
import com.bondarenko.academicsystem.services.GroupService;
import com.bondarenko.academicsystem.services.LectureService;
import com.bondarenko.academicsystem.services.StudentService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Slf4j
@Controller
@AllArgsConstructor
@RequestMapping("/admin")
public class AddUserController {

    private final CustomUserDetailsService customUserDetailsService;
    private final GroupService groupService;
    private final StudentService studentService;
    private final LectureService lectureService;

    @GetMapping("/add-user")
    public String addUserPage(Model model) {
        List<GroupDto> groups = groupService.getAllGroups();
        model.addAttribute("groups", groups);
        return "CreateUser";
    }

    @PostMapping("/add-user")
    public String addUser(@Valid @ModelAttribute CreateUserDto createUserDto, Model model) {
        User user = new User(createUserDto);

        if (customUserDetailsService.userExists(createUserDto.getUsername())) {
            model.addAttribute("error", "Username already exists. Please choose another.");
            List<GroupDto> groups = groupService.getAllGroups();
            model.addAttribute("groups", groups);
            return "CreateUser";
        }

        switch (user.getRole()) {
            case ADMIN: {
                customUserDetailsService.createUser(user);
                break;
            }
            case STUDENT: {
                studentService.saveStudent(user, createUserDto.getGroup());
                break;
            }
            case LECTURE: {
                lectureService.addLecture(user);
                break;
            }
            default: {
                log.info("Role not recognized for username: {}", createUserDto.getUsername());
            }
        }

        return "redirect:/admin/add-user";
    }
}


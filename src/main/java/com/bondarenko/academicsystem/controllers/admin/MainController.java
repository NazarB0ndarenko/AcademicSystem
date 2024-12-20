package com.bondarenko.academicsystem.controllers;

import com.bondarenko.academicsystem.dto.CreateUserDto;
import com.bondarenko.academicsystem.dto.GroupDto;
import com.bondarenko.academicsystem.enteties.Student;
import com.bondarenko.academicsystem.enteties.User;
import com.bondarenko.academicsystem.services.CustomUserDetailsService;
import com.bondarenko.academicsystem.services.GroupService;
import com.bondarenko.academicsystem.services.StudentService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@Controller
@AllArgsConstructor
@RequestMapping("/admin")
public class AdminController {

    private final CustomUserDetailsService customUserDetailsService;
    private final GroupService groupService;
    private final StudentService studentService;

    @GetMapping("/dashboard")
    public String admin() {
        return "AdminDashboard";
    }

    @GetMapping("/add-user")
    public String addUserPage(Model model) {
        List<GroupDto> groups = groupService.getAllGroups();
        model.addAttribute("groups", groups);
        return "CreateUser";
    }

    @PostMapping("/add-user")
    public String addUser(@Valid @ModelAttribute CreateUserDto createUserDto) {
        User user = new User(createUserDto);
        switch (user.getRole()) {
            case ADMIN: {
                log.info("Creating admin with username: {}", createUserDto.getUsername());

                customUserDetailsService.createUser(user);

                log.info("Created admin username: {}", createUserDto.getUsername());
                break;
            }
            case STUDENT: {

                log.info("Creating student with username: {}", createUserDto.getUsername());

                Student student = new Student(user, createUserDto.getGroup());
                studentService.saveStudent(student);

                log.info("Creating student with username: {}", createUserDto.getUsername());

                break;
            }
            case LECTURE: {
                log.info("Lecturer created with username: {}", createUserDto.getUsername());
                customUserDetailsService.createUser(user);
                break;
            }
            default: {
                log.info("Role not recognized for username: {}", createUserDto.getUsername());
            }
        }

        return "redirect:/admin/add-user";
    }

}

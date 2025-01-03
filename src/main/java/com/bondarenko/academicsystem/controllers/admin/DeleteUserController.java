package com.bondarenko.academicsystem.controllers.admin;

import com.bondarenko.academicsystem.dto.user.UserIdRoleDto;
import com.bondarenko.academicsystem.services.CustomUserDetailsService;
import com.bondarenko.academicsystem.services.LectureService;
import com.bondarenko.academicsystem.services.StudentService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


@Controller
@AllArgsConstructor
public class DeleteUserController {

    private final CustomUserDetailsService customUserDetailsService;
    private final StudentService studentService;
    private final LectureService lectureService;

    @GetMapping("/admin/delete-user")
    public String deleteUser() {
        return "deleteUser";
    }

    @Transactional
    @PostMapping("/admin/delete-user")
    public String deleteUser(@RequestParam String username) {

        UserIdRoleDto userIdRoleDto = customUserDetailsService.findUserRole(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found."));

        switch (userIdRoleDto.getRole()) {
            case STUDENT: {
                studentService.deleteStudent(userIdRoleDto.getUserId());
                break;
            }
            case LECTURE: {
                lectureService.deleteLecture(userIdRoleDto.getUserId());
                break;
            }
            case ADMIN: {
                customUserDetailsService.deleteUser(username);
                break;
            }
        }
        return "redirect:/admin/delete-user";
    }
}

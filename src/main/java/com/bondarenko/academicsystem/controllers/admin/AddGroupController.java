package com.bondarenko.academicsystem.controllers.admin;

import com.bondarenko.academicsystem.dto.group.CreateGroupDto;
import com.bondarenko.academicsystem.services.GroupService;
import com.bondarenko.academicsystem.services.StudentService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@AllArgsConstructor
public class AddGroupController {

    private final StudentService studentService;
    private final GroupService groupService;

    @GetMapping("/admin/add-group")
    public String addGroup(Model model) {
        model.addAttribute("students", studentService.findStudentsWithoutGroup());
        return "AddGroup";
    }

    @PostMapping("/admin/add-group")
    public String addGroup(@ModelAttribute CreateGroupDto createGroupDto, RedirectAttributes redirectAttributes, Model model) {
        if (groupService.groupNameExists(createGroupDto.getName())) {
            model.addAttribute("error", "Group name already exists. Please choose another.");
            model.addAttribute("students", studentService.findStudentsWithoutGroup());
            return "AddGroup";
        }

        groupService.saveGroup(createGroupDto);
        redirectAttributes.addFlashAttribute("message", "Group added successfully.");
        return "redirect:/admin/add-group";
    }
}


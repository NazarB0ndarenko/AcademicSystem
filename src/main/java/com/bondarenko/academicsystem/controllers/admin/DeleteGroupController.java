package com.bondarenko.academicsystem.controllers.admin;

import com.bondarenko.academicsystem.dto.GroupDto;
import com.bondarenko.academicsystem.services.GroupService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@AllArgsConstructor
public class DeleteGroupController {

    private final GroupService groupService;

    @GetMapping("/admin/delete-group")
    public String deleteGroup(Model model) {
        List<GroupDto> groups = groupService.getAllGroups();
        model.addAttribute("groups", groups);
        return "DeleteGroup";
    }

    @PostMapping("/admin/delete-group")
    public String deleteGroup(@ModelAttribute Long groupId) {
        groupService.deleteGroup(groupId);
        return "redirect:/admin/delete-group";
    }
}

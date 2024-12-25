package com.bondarenko.academicsystem.controllers.admin;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Slf4j
@Controller
@AllArgsConstructor
@RequestMapping("/admin")
public class MainController {

    @GetMapping("/dashboard")
    public String admin() {
        return "AdminDashboard";
    }

}

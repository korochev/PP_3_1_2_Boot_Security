package ru.kata.spring.boot_security.demo.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;

import java.security.Principal;

@Controller
public class UserController {
    private final UserService userSrv;
    private final RoleService roleSrv;

    public UserController(UserService userSrv, RoleService roleSrv) {
        this.userSrv = userSrv;
        this.roleSrv = roleSrv;
    }

    @GetMapping()
    public String getStartPage() {
        return "redirect:/user";
    }

    @GetMapping("/user")
    public String getLk(Principal principal, Model model) {
        User user = userSrv.findByUsername(principal.getName());
        model.addAttribute("User", user);
        model.addAttribute("currentName", principal.getName());
        return "show";
    }

}
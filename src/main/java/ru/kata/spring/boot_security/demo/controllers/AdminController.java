package ru.kata.spring.boot_security.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.models.Role;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;

import java.security.Principal;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final UserService userSrv;
    private final RoleService roleSrv;

    @Autowired
    public AdminController(UserService userService, RoleService roleService) {
        this.roleSrv = roleService;
        this.userSrv = userService;
    }



    @GetMapping()
    public String index(Model model, Principal principal) {
        model.addAttribute("users", userSrv.index());
        model.addAttribute("currentName", principal.getName());
        return "main";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable("id") long id, Model model, Principal principal) {
        User user = userSrv.show(id);
        model.addAttribute("User", user);
        model.addAttribute("currentName", principal.getName());
        return "show";
    }

    @GetMapping("/new")
    public String newUser(@ModelAttribute("User") User User, Model model, Principal principal) {
        model.addAttribute("rolesList", roleSrv.index());
        model.addAttribute("currentName", principal.getName());
        return "new";
    }

    @PostMapping()
    public String create(@ModelAttribute("User") User User) {
        if (!userSrv.save(User))
            return "new";

        return "redirect:/admin/";
    }

    @GetMapping("/{id}/edit")
    public String edit(Model model, @PathVariable("id") long id, Principal principal) {
        model.addAttribute("User", userSrv.show(id));
        model.addAttribute("rolesList", roleSrv.index());
        model.addAttribute("currentName", principal.getName());
        return "edit";
    }

    @PatchMapping("/{id}")
    public String update(@ModelAttribute("User") User User) {
        if (!userSrv.save(User))
            return "edit";
        return "redirect:/admin/";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") long id) {
        userSrv.delete(id);
        return "redirect:/admin/";
    }
}

package ru.kata.spring.boot_security.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.models.Role;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;

import java.util.List;
import java.util.Set;


@Controller
public class AdminController {

    private final UserService userService;
    private final RoleService roleService;

    @Autowired
    public AdminController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping("/admin")
    public String showUsers(Model model) {
        model.addAttribute("users", userService.getUserTable());
        model.addAttribute("roles", roleService.getAllRoles());
        return "adminpage";
    }


    @GetMapping("/admin/new")
    public String newUser(@AuthenticationPrincipal User user, Model model) {
        model.addAttribute("user", new User());
        model.addAttribute("roles", roleService.getAllRoles());
        return "new";

    }

    @PostMapping("/admin/new")
    public String create(@ModelAttribute("user") User user, Model model, @RequestParam(value = "ids") List<Long> ids) {
        Set<Role> role = roleService.getAllRolesById(ids);
        user.setRole(role);
        userService.addUser(user);
        return "redirect:/admin";
    }

    @GetMapping("/admin/{id}/edit")
    public String editUser(Model model, @PathVariable("id") long id) {
        model.addAttribute("user", userService.findUser(id));
        model.addAttribute("roles", roleService.getAllRoles());
        return "edit";
    }

    @PostMapping("/admin/edit")
    public String update(@ModelAttribute("user") User user, Model model, @RequestParam(value = "ids", required = false) List<Long> ids) {
        Set<Role> role = roleService.getAllRolesById(ids);
        user.setRole(role);
        userService.updateUser(user);
        return "redirect:/admin";
    }

    @GetMapping("/admin/delete/{id}")
    public String delete(@PathVariable("id") long id) {
        userService.deleteUser(id);
        return "redirect:/admin";
    }
}
package ru.kata.spring.boot_security.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.models.Role;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;

import javax.validation.Valid;
import java.util.List;
import java.util.Set;

@Controller
@RequestMapping({"/admin"})
public class AdminController {
    private static final String REDIRECT_ADMIN = "redirect:/admin";

    private final UserService userService;
    private final RoleService roleService;

    @Autowired
    public AdminController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping
    public String showUsers(Model model,@AuthenticationPrincipal  User user) {
        model.addAttribute("user", user);
        model.addAttribute("users", userService.getUserTable());
        model.addAttribute("roles", roleService.getAllRoles());
        return "Adminp";
    }

    @GetMapping("/admin/new")
    public String newUser(@AuthenticationPrincipal User user, Model model) {
        model.addAttribute("user", new User());
        model.addAttribute("roles", roleService.getAllRoles());
        return "new";
    }

    @PostMapping("/new")
    public String create(@Valid @ModelAttribute("user") User user, BindingResult bindingResult, Model model, @RequestParam(value = "ids") List<Long> ids) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("roles", roleService.getAllRoles());
            return "/new";
        } else {
            Set<Role> role = roleService.getAllRolesById(ids);
            user.setRole(role);
            userService.addUser(user);
            return REDIRECT_ADMIN;
        }
    }

    @GetMapping("/{id}/edit")
    public String editUser(Model model, @PathVariable("id") Long id) {
        model.addAttribute("user", userService.findUser(id));
        model.addAttribute("roles", roleService.getAllRoles());
        return REDIRECT_ADMIN;
    }

    @PostMapping("/edit/{id}")
    public String update(@ModelAttribute("user") User user, Model model, @RequestParam(value = "ids", required = false) List<Long> ids) {
        Set<Role> role = roleService.getAllRolesById(ids);
        user.setRole(role);
        userService.updateUser(user);
        return REDIRECT_ADMIN;
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable("id") long id) {
        userService.deleteUser(id);
        return REDIRECT_ADMIN;
    }
}

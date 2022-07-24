package ru.wertor.bootstrap.demo.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.wertor.bootstrap.demo.model.Role;
import ru.wertor.bootstrap.demo.model.User;
import ru.wertor.bootstrap.demo.repository.RoleRepository;
import ru.wertor.bootstrap.demo.service.UserService;

import java.util.List;

@Controller
@RequestMapping(value = "/admin")
public class AdminController {

    private final UserService userService;
    private final RoleRepository roleRepository;

    public AdminController(UserService userService, RoleRepository roleRepository) {
        this.userService = userService;
        this.roleRepository = roleRepository;
    }

    @GetMapping(value = "/users")
    public String showUsers(Model model, @AuthenticationPrincipal User admin, @ModelAttribute("newUser") User newUser) {
        List<Role> roles = roleRepository.findAll();
        model.addAttribute("admin", admin);
        model.addAttribute("users", userService.findAll());
        model.addAttribute("newUser", newUser);
        model.addAttribute("roles", roles);
        return "admin/users";
    }

    @PostMapping("/users")
    public String createUser(@ModelAttribute("user") User user) {
        userService.saveUser(user);
        return "redirect:/admin/users";
    }

    @DeleteMapping(value = "/users")
    public String deleteUser(@ModelAttribute("user") User user) {
        userService.deleteUser(user);
        return "redirect:/admin/users";
    }

    @PatchMapping(value = "/users")
    public String updateUser(@ModelAttribute("editUser") User editUser) {
        userService.updateUser(editUser);
        return "redirect:/admin/users";
    }
}

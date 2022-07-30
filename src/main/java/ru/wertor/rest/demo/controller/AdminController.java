package ru.wertor.rest.demo.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import ru.wertor.rest.demo.model.Role;
import ru.wertor.rest.demo.model.User;
import ru.wertor.rest.demo.repository.RoleRepository;
import ru.wertor.rest.demo.service.UserService;

import java.util.List;

@RestController
@RequestMapping(value = "/api")
@CrossOrigin(origins = "*")
public class AdminController {

    private final UserService userService;
    private final RoleRepository roleRepository;

    public AdminController(UserService userService, RoleRepository roleRepository) {
        this.userService = userService;
        this.roleRepository = roleRepository;
    }

    @GetMapping(value = "/users")
    public ResponseEntity<List<User>> showUsers() {
        return new ResponseEntity<>(userService.findAll(), HttpStatus.OK);
    }

    @GetMapping(value = "/roles")
    public ResponseEntity<List<Role>> getRoles() {
        return new ResponseEntity<>(roleRepository.findAll(), HttpStatus.OK);
    }

    @PostMapping(value = "/users")
    public ResponseEntity<User> createUser(@RequestBody User user) {
        userService.saveUser(user);
        return new ResponseEntity<>(user, HttpStatus.CREATED);
    }

    @GetMapping("/viewUser")
    public ResponseEntity<User> showUser(@AuthenticationPrincipal User user) {
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @DeleteMapping(value = "/users/{id}")
    public ResponseEntity<User> deleteUser(@PathVariable Long id) {
        userService.deleteUserById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<User> showUserById(@PathVariable Long id) {
        User user = userService.findById(id);
        return user != null
                ? new ResponseEntity<>(user, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PatchMapping(value = "/users/{id}")
    public ResponseEntity<User> updateUser(@RequestBody User user) {
        userService.updateUser(user);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
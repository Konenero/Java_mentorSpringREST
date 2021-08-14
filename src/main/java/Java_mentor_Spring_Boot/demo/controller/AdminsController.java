package Java_mentor_Spring_Boot.demo.controller;

import Java_mentor_Spring_Boot.demo.model.Role;
import Java_mentor_Spring_Boot.demo.model.User;
import Java_mentor_Spring_Boot.demo.service.RoleService;
import Java_mentor_Spring_Boot.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


import java.security.Principal;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class AdminsController {
    private UserService userService;
    private RoleService roleService;

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Autowired
    public void setRoleService(RoleService roleService) {
        this.roleService = roleService;
    }

    @GetMapping("/allUsers")
    public ResponseEntity<List<User>> getUsers() {
        return ResponseEntity.ok(userService.listUsers());
    }

    @PostMapping("/adduser")
    public User addUser(@RequestBody User user) {
        userService.saveUser(user);
        return user;
    }

    @PutMapping("/edit/{id}")
    public User updateUser(@RequestBody User user) {
        userService.updateUser(user);
        return user;
    }

    @DeleteMapping("/{id}/delete")
    public void deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
    }

}

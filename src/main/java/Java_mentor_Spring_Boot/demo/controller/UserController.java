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
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


import java.net.Authenticator;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/")
public class UserController {
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

    @RequestMapping(value = "hello", method = RequestMethod.GET)
    public String printWelcome(ModelMap model) {
        List<String> messages = new ArrayList<>();
        messages.add("Hello!");
        messages.add("I'm Spring MVC-SECURITY application");
        messages.add("5.2.0 version by sep'19 ");
        model.addAttribute("messages", messages);
        return "hello";
    }

    @RequestMapping(value = "login", method = RequestMethod.GET)
    public String loginPage() {
        return "login";
    }

    @GetMapping("/user")
    public String userPage(Model model, Authentication authentication) {
        model.addAttribute("firstrole", authentication.getAuthorities().stream().map(Object::toString).collect(Collectors.joining(" ")));
        model.addAttribute("firstuser", authentication.getName());
        User user = userService.findUserByName(authentication.getName());
        model.addAttribute("user", user);
        return "user";
    }

    @GetMapping("/admin")
    public String getUsers(Authentication authentication, Model model) {
        List<User> users = userService.listUsers();
        List<Role> roles = roleService.getRolesList();
        model.addAttribute("allRoles", roles);
        model.addAttribute("firstrole", authentication.getAuthorities().stream().map(Object::toString).collect(Collectors.joining(" ")));
        model.addAttribute("firstuser", authentication.getName());
        model.addAttribute("users", users);
        return "users";
    }

    @GetMapping("/new")
    public String newUserForm(Model model, Authentication authentication) {
        model.addAttribute(new User());
        User user = userService.findUserByName(authentication.getName());
        List<Role> roles = roleService.getRolesList();
        model.addAttribute("allRoles", roles);
        model.addAttribute("firstrole", authentication.getAuthorities().stream().map(Object::toString).collect(Collectors.joining(" ")));
        model.addAttribute("firstuser", authentication.getName());
        return "create";
    }
}
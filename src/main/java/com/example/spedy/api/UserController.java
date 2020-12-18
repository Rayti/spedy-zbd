package com.example.spedy.api;


import com.example.spedy.model.User;
import com.example.spedy.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(@Qualifier("userService") UserService userService) {
        this.userService = userService;
    }

    @GetMapping("users")
    public String showAllUsers(Model model) {
        List<User> users = userService.getUsers();
        model.addAttribute("users", users);
        model.addAttribute("loginSearch", "");
        return "users";
    }

    @PostMapping("users")
    public String deleteUser(@RequestParam String deleteLogin, Model model) {
        User user = userService.getUser(deleteLogin);
        String message = userService.deleteUser(user);
        model.addAttribute("message", message);
        return "info";
    }

    @GetMapping("users/search")
    public String showSpecificUsers(@RequestParam String loginSearch, Model model) {
        List<User> users = userService.getSpecificUsers(loginSearch);
        model.addAttribute("users", users);
        model.addAttribute("loginSearch", loginSearch);
        return "users";
    }

    @GetMapping("users/update")
    public String updateUserForm(@RequestParam String login, Model model) {
        User user = userService.getUser(login);
        model.addAttribute("user", user);
        return "usersUpdate";
    }

    @PostMapping("users/update")
    public String updateUser(@RequestParam String oldLogin,
                             @RequestParam String newLogin,
                             @RequestParam String newPassword,
                             Model model) {
        User user = userService.getUser(oldLogin);
        if (newLogin.length() < 3) {
            model.addAttribute("message", "Login should have at least 3 characters.");
            return "error";
        }
        else {
            user.setLogin(newLogin);
            user.setPassword(newPassword);
            String message = userService.updateUser(user);
            model.addAttribute("message", message);
            return "info";
        }
    }

    @GetMapping("users/create")
    public String createUserForm(Model model) {
        return "usersCreation";
    }

    @PostMapping("users/create")
    public String createUser(@RequestParam String newLogin,
                             @RequestParam String newPassword,
                             Model model) {
        User user = new User(newLogin, newPassword);
        String message = userService.insertUser(user);
        model.addAttribute("message", message);
        return "info";
    }


/*    public String createNewUser(@ModelAttribute User user,  Model model) {
        userService.insertUser(user);
    }*/


}

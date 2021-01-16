package com.example.spedy.api;


import com.example.spedy.model.User;
import com.example.spedy.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
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
        return "users/users";
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
        return "users/users";
    }

    @GetMapping("users/update")
    public String updateUserForm(@RequestParam String login, Model model) {
        User user = userService.getUser(login);
        model.addAttribute("user", user);
        return "users/usersUpdate";
    }

    @PostMapping("users/update")
    public String updateUser(@RequestParam String oldLogin,
                             @RequestParam String newLogin,
                             @RequestParam String newPassword,
                             Model model) {
        User user = changeUserLoginAndPassword(oldLogin, newLogin, newPassword);
        String message = userService.updateUser(user);
        model.addAttribute("message", message);
        return "info";
    }

    @NonNull
    private User changeUserLoginAndPassword(String oldLogin, String newLogin, String newPassword) {
        User user = userService.getUser(oldLogin);
        user.setLogin(newLogin);
        user.setPassword(newPassword);
        return user;
    }

    @GetMapping("users/create")
    public String createUserForm(Model model) {
        return "users/usersCreation";
    }

    @PostMapping("users/create")
    public String createUser(HttpServletRequest request, Model model) {
        User user = new User(
                request.getParameter("newLogin"),
                request.getParameter("newPassword"));
        String message = userService.insertUser(user);
        model.addAttribute("message", message);
        return "info";
    }
}

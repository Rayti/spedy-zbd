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
        addToModel(model, null, users, "");
        return "users/users";
    }

    @PostMapping("users")
    public String deleteUser(@RequestParam String deleteLogin,
                             @RequestParam String loginSearch,
                             Model model) {
        User user = userService.getUser(deleteLogin);
        String message = userService.deleteUser(user);
        addToModel(model, message, userService.getSpecificUsers(loginSearch), loginSearch);
        return "users/users";
    }

    @GetMapping("users/search")
    public String showSpecificUsers(@RequestParam String loginSearch, Model model) {
        List<User> users = userService.getSpecificUsers(loginSearch);
        addToModel(model, null, users, loginSearch);
        return "users/users";
    }

    @GetMapping("users/update")
    public String updateUserForm(@RequestParam String login,
                                 @RequestParam String loginSearch,
                                 Model model) {
        User user = userService.getUser(login);
        model.addAttribute("user", user);
        addToModel(model, null, null, loginSearch);
        return "users/usersUpdate";
    }

    @PostMapping("users/update")
    public String updateUser(@RequestParam String oldLogin,
                             @RequestParam String newLogin,
                             @RequestParam String newPassword,
                             @RequestParam String loginSearch,
                             Model model) {
        User user = changeUserLoginAndPassword(oldLogin, newLogin, newPassword);
        String message = userService.updateUser(user);
        addToModel(model, message, userService.getSpecificUsers(loginSearch), loginSearch);
        return "users/users";
    }

    @NonNull
    private User changeUserLoginAndPassword(String oldLogin, String newLogin, String newPassword) {
        User user = userService.getUser(oldLogin);
        user.setLogin(newLogin);
        user.setPassword(newPassword);
        return user;
    }

    @GetMapping("users/create")
    public String createUserForm(@RequestParam String loginSearch,
                                 Model model) {
        model.addAttribute("loginSearch", loginSearch);
        return "users/usersCreation";
    }

    @PostMapping("users/create")
    public String createUser(HttpServletRequest request, Model model) {
        User user = new User(
                request.getParameter("newLogin"),
                request.getParameter("newPassword"));
        String loginSearch = request.getParameter("loginSearch");
        String message = userService.insertUser(user);
        addToModel(model, message, userService.getSpecificUsers(loginSearch), loginSearch);
        return "users/users";
    }

    private void addToModel(Model model, String message, List<User> users, String loginSearch){
        model.addAttribute("message", message);
        model.addAttribute("users", users);
        model.addAttribute("loginSearch", loginSearch);
    }
}

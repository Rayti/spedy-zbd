package com.example.spedy.api;

import com.example.spedy.model.User;
import com.example.spedy.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/*TODO
       template
       add user
       delete user
       update user
       show all users
       show specific user by login
       transfer to endpoint with employee connected to that user
 */

@RestController
@RequestMapping("/users")
public class UserControllerRest {

    private final UserService userService;

    @Autowired
    public UserControllerRest(@Qualifier("userService") UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/user2")
    public List<User> getUsers() {
        return  userService.getUsers();
    }

}

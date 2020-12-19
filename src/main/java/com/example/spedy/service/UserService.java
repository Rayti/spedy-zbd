package com.example.spedy.service;


import com.example.spedy.dao.SimpleDao;
import com.example.spedy.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service("userService")
public class UserService {

    private final SimpleDao<User> dao;

    @Autowired
    public UserService(@Qualifier("postgresUserDao") SimpleDao<User> userDao) {
        this.dao = userDao;
    }

    public User getUser(User user) {
        return dao.select(user);
    }

    public User getUser(String login) {
        return dao.select(login);
    }

    public User getUser(UUID id) {
        return dao.select(id);
    }

    public List<User> getUsers() {
        return dao.selectAll();
    }

    public List<User> getSpecificUsers(String loginPattern) {
        List<User> userList = dao.selectAll();
        return userList.stream()
                .filter(user -> user.getLogin().toLowerCase().contains(loginPattern.toLowerCase()))
                .collect(Collectors.toList());
    }

    public String deleteUser(User user) {
        String responseIfTrue = "User " + user.getLogin() + " deleted from database.";
        String responseIfFalse = "Could not delete user. Check spelling.";
        return dao.delete(user) ? responseIfTrue : responseIfFalse;
    }

    public String insertUser(User user) {
        String login = user.getLogin();
        if (existsUserWithSameName(user)) {
            return "User with this name already exists";
        }
        String responseIfTrue = "User " + user.getLogin() + " added to database.";
        String responseIfFalse = "Could not add new user. Check spelling.";
        return dao.insert(user) ? responseIfTrue : responseIfTrue;
    }

    public String updateUser(User user) {
        if (existsUserWithSameName(user)) {
            return "User with this name already exists.";
        }
        String responseIfTrue = "User updated.";
        String responseIfFalse = "Could not update user. Check spelling.";
        return dao.update(user) ? responseIfTrue : responseIfFalse;
    }

    private boolean existsUserWithSameName(User user) {
        return dao.selectAll()
                .stream()
                .anyMatch(daoUser -> daoUser.getLogin().equals(user.getLogin())
                && daoUser.getUserId().compareTo(user.getUserId()) != 0);
    }

}

package com.example.spedy.service;


import com.example.spedy.dao.UserDao;
import com.example.spedy.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service("userService")
public class UserService {

    private final UserDao userDao;

    @Autowired
    public UserService(@Qualifier("postgresUserDao") UserDao userDao) {
        this.userDao = userDao;
    }

    public User getUser(User user) {
        return userDao.selectUser(user);
    }

    public User getUser(String login) {
        return userDao.selectUser(login);
    }

    public List<User> getUsers() {
        return userDao.selectUsers();
    }

    public List<User> getSpecificUsers(String loginPattern) {
        List<User> userList = userDao.selectUsers();
        return userList.stream()
                .filter(user -> user.getLogin().toLowerCase().contains(loginPattern.toLowerCase()))
                .collect(Collectors.toList());
    }

    public String deleteUser(User user) {
        String responseIfTrue = "User " + user.getLogin() + " deleted from database.";
        String responseIfFalse = "Could not delete user. Check spelling.";
        return userDao.deleteUser(user) ? responseIfTrue : responseIfFalse;
    }

    public String insertUser(User user) {
        String login = user.getLogin();
        if (existsUserWithSameName(login)) {
            return "User with this name already exists";
        }
        String responseIfTrue = "User " + user.getLogin() + " added to database.";
        String responseIfFalse = "Could not add new user. Check spelling.";
        return userDao.insertUser(user) ? responseIfTrue : responseIfTrue;
    }

    public String updateUser(User user) {
        if (existsUserWithSameName(user.getLogin())) {
            return "User with this name already exists.";
        }
        String responseIfTrue = "User updated.";
        String responseIfFalse = "Could not update user. Check spelling.";
        return userDao.updateUser(user) ? responseIfTrue : responseIfFalse;
    }

    private boolean existsUserWithSameName(String login) {
        return userDao.selectUsers()
                .stream()
                .anyMatch(daoUser -> daoUser.getLogin().equals(login));
    }




}

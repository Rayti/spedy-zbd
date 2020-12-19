package com.example.spedy.dao;

import com.example.spedy.model.User;

import java.util.List;
import java.util.UUID;

public interface UserDao {
    boolean insertUser(User user);

    boolean updateUser(User user);

    boolean deleteUser(User user);

    List<User> selectUsers();

    User selectUser(User user);

    User selectUser(String login);

    User selectUser(UUID id);
}

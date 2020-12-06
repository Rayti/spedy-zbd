package com.example.spedy.model;

import java.util.UUID;

public class User {

    private final UUID userId;
    private String login;
    private String password;


    public User(String login, String password) {
        this.userId = UUID.randomUUID();
        this.login = login;
        this.password = password;
    }

    public User(UUID userId, String login, String password) {
        this.userId = userId;
        this.login = login;
        this.password = password;
    }

    public UUID getUserId() {
        return userId;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        if (!userId.equals(user.userId)) return false;
        return login.equals(user.login);
    }

    @Override
    public int hashCode() {
        int result = userId.hashCode();
        result = 31 * result + login.hashCode();
        return result;
    }
}

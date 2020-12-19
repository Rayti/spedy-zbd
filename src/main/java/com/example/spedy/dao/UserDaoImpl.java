package com.example.spedy.dao;


import com.example.spedy.model.User;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.UUID;

@Repository("postgresUserDao")
public class UserDaoImpl implements UserDao {

    private final JdbcTemplate jdbcTemplate;
    private static final String INSERT = "INSERT INTO app_users(user_id, login, password) VALUES (?, ?, ?)";
    private static final String UPDATE = "UPDATE app_users SET login = ?, password = ? WHERE user_id = ?";
    private static final String DELETE = "DELETE FROM app_users WHERE user_id = ?";
    private static final String SELECT_ALL = "SELECT * FROM app_users;";
    private static final String SELECT_ONE_BY_ID = "SELECT * FROM app_users WHERE user_id = ?";
    private static final String SELECT_ONE_BY_LOGIN = "SELECT * FROM app_users WHERE login = ?";

    public UserDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public boolean insertUser(User user) {
        return jdbcTemplate.update(INSERT,user.getUserId(), user.getLogin(), user.getPassword()) > 0;
    }

    @Override
    public boolean updateUser(User user) {
        return jdbcTemplate.update(UPDATE, user.getLogin(), user.getPassword(), user.getUserId()) > 0;
    }

    @Override
    public boolean deleteUser(User user) {
        return jdbcTemplate.update(DELETE, user.getUserId()) > 0;
    }

    @Override
    public List<User> selectUsers() {
        return jdbcTemplate.query(SELECT_ALL, new UserRowMapper());
    }

    @Override
    public User selectUser(UUID id) {
        return jdbcTemplate.queryForObject(SELECT_ONE_BY_ID, new UserRowMapper(), id);
    }

    @Override
    public User selectUser(String login) {
        try{
            return jdbcTemplate.queryForObject(SELECT_ONE_BY_LOGIN, new UserRowMapper(), login);
        }catch (Exception e){
            return null;
        }
    }

    @Override
    public User selectUser(User user) {
        return selectUser(user.getUserId());
    }

    private class UserRowMapper implements RowMapper<User> {
        @Override
        public User mapRow(ResultSet resultSet, int i) throws SQLException {
            UUID user_id = (UUID)(resultSet.getObject("user_id"));
            String login = resultSet.getString("login");
            String password = resultSet.getString("password");
            return new User(user_id, login, password);
        }
    }
}


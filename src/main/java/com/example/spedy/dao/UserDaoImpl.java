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
public class UserDaoImpl implements SimpleDao<User> {

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
    public boolean insert(User model) {
        return jdbcTemplate.update(INSERT,model.getUserId(), model.getLogin(), model.getPassword()) > 0;
    }

    @Override
    public boolean update(User model) {
        return jdbcTemplate.update(UPDATE, model.getLogin(), model.getPassword(), model.getUserId()) > 0;
    }

    @Override
    public boolean delete(User model) {
        return jdbcTemplate.update(DELETE, model.getUserId()) > 0;
    }

    @Override
    public List<User> selectAll() {
        return jdbcTemplate.query(SELECT_ALL, new UserRowMapper());
    }

    @Override
    public User select(UUID id) {
        return jdbcTemplate.queryForObject(SELECT_ONE_BY_ID, new UserRowMapper(), id);
    }

    @Override
    public User select(String name) {
        try{
            return jdbcTemplate.queryForObject(SELECT_ONE_BY_LOGIN, new UserRowMapper(), name);
        }catch (Exception e){
            return null;
        }
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


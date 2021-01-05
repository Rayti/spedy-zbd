package com.example.spedy.dao;

import com.example.spedy.model.Cargo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.UUID;

@Repository("postgresCargoDao")
public class CargoDaoImp implements SimpleDao<Cargo> {

    private final JdbcTemplate jdbcTemplate;
    private final static String INSERT = "INSERT INTO cargo(cargo_id, name, description)" +
            "VALUES(?, ?, ?)";
    private final static String DELETE = "DELETE FROM cargo WHERE cargo_id = ?";
    private final static String UPDATE = "UPDATE cargo SET name = ?, description = ?" +
            "WHERE cargo_id = ?";
    private final static String SELECT_ALL = "SELECT * FROM cargo";
    private final static String SELECT_ONE_BY_ID = "SELECT * FROM cargo WHERE cargo_id = ?";
    private final static String SELECT_ONE_BY_NAME = "SELECT * FROM cargo WHERE name = ?";


    @Autowired
    public CargoDaoImp(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public boolean insert(Cargo cargo) {
        return jdbcTemplate.update(INSERT, cargo.getId(), cargo.getName(), cargo.getDescription()) > 0;
    }

    @Override
    public boolean delete(Cargo cargo) {
        return jdbcTemplate.update(DELETE, cargo.getId()) > 0;
    }

    @Override
    public boolean update(Cargo cargo) {
        return jdbcTemplate.update(UPDATE, cargo.getName(), cargo.getDescription(), cargo.getId()) > 0;
    }

    @Override
    public List<Cargo> selectAll() {
        return jdbcTemplate.query(SELECT_ALL, new CargoRowMapper());
    }

    @Override
    public Cargo select(UUID id) {
        return jdbcTemplate.queryForObject(SELECT_ONE_BY_ID, new CargoRowMapper(), id);
    }

    @Override
    public Cargo select(String name) {
        try {
            return jdbcTemplate.queryForObject(SELECT_ONE_BY_NAME, new CargoRowMapper(), name);
        } catch (Exception e) {
            return null;
        }
    }

    private class CargoRowMapper implements RowMapper<Cargo> {
        @Override
        public Cargo mapRow(ResultSet resultSet, int i) throws SQLException {
            UUID id = (UUID) resultSet.getObject("cargo_id");
            String name = resultSet.getString("name");
            String description = resultSet.getString("description");
            return new Cargo(id, name, description);
        }
    }

}

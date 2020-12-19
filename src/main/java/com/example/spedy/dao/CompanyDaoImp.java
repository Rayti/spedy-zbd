package com.example.spedy.dao;

import com.example.spedy.model.Company;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.UUID;

@Repository("postgresCompanyDao")
public class CompanyDaoImp implements SimpleDao<Company> {

    private final JdbcTemplate jdbcTemplate;
    private final static String INSERT = "INSERT INTO companies" +
            "(company_id, name, description, country, city, address)" +
            "VALUES(?, ?, ?, ?, ?, ?)";
    private final static String DELETE = "DELETE FROM companies WHERE company_id = ?";
    private final static String UPDATE = "UPDATE companies " +
            "SET name = ?, description = ?, country = ?, city = ?, address = ?" +
            "WHERE company_id = ?";
    private final static String SELECT_ALL = "SELECT * FROM companies";
    private final static String SELECT_ONE_BY_ID = "SELECT * FROM companies " +
            "WHERE company_id = ?";
    private final static String SELECT_ONE_BY_NAME = "SELECT * FROM companies WHERE name = ?";

    @Autowired
    public CompanyDaoImp(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public boolean insert(Company model) {
        return jdbcTemplate.update(INSERT, model.getId(), model.getName(), model.getDescription(),
                model.getCountry(), model.getCity(), model.getAddress()) > 0;
    }

    @Override
    public boolean update(Company model) {
        return jdbcTemplate.update(UPDATE, model.getName(), model.getDescription(),
                model.getCountry(), model.getCity(), model.getAddress(), model.getId()) > 0;
    }

    @Override
    public boolean delete(Company model) {
        return jdbcTemplate.update(DELETE, model.getId()) > 0;
    }

    @Override
    public List<Company> selectAll() {
        return jdbcTemplate.query(SELECT_ALL, new CompanyRowMapper());
    }

    @Override
    public Company select(String name) {
        try {
            return jdbcTemplate.queryForObject(SELECT_ONE_BY_NAME, new CompanyRowMapper(), name);
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public Company select(UUID id) {
        return jdbcTemplate.queryForObject(SELECT_ONE_BY_ID, new CompanyRowMapper(), id);
    }

    @Override
    public Company select(Company model) {
        return select(model.getId());
    }

    private class CompanyRowMapper implements RowMapper<Company> {
        @Override
        public Company mapRow(ResultSet resultSet, int i) throws SQLException {
            UUID id = (UUID)resultSet.getObject("company_id");
            String name = resultSet.getString("name");
            String description = resultSet.getString("description");
            String country = resultSet.getString("country");
            String city = resultSet.getString("city");
            String address = resultSet.getString("address");
            return new Company(
                    id,
                    name,
                    description,
                    country,
                    city,
                    address);
        }
    }

}

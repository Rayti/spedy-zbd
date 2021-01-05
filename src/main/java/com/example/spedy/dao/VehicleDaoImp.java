package com.example.spedy.dao;

import com.example.spedy.model.Vehicle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.UUID;

@Repository("postgresVehicleDao")
public class VehicleDaoImp implements SimpleDao<Vehicle> {

    private final JdbcTemplate jdbcTemplate;
    private final static String INSERT = "INSERT INTO vehicles(vehicle_id, name, power, production_year) " +
            "VALUES(?, ?, ?, ?)";
    private final static String DELETE = "DELETE FROM vehicles WHERE vehicle_id = ?";
    private final static String UPDATE = "UPDATE vehicles SET " +
            "name = ?, power = ?, production_year = ?" +
            "WHERE vehicle_id = ?";
    private final static String SELECT_ALL = "SELECT * FROM vehicles";
    private final static String SELECT_ONE_BY_ID = "SELECT * FROM vehicles WHERE vehicle_id = ?";
    private final static String SELECT_ONE_BY_NAME = "SELECT * FROM vehicles WHERE name = ?";


    @Autowired
    public VehicleDaoImp(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public boolean insert(Vehicle model) {
        return jdbcTemplate.update(INSERT,
                model.getId(),
                model.getName(),
                model.getPower(),
                model.getProductionYear()) > 0;
    }

    @Override
    public boolean update(Vehicle model) {
        return jdbcTemplate.update(UPDATE,
                model.getName(),
                model.getPower(),
                model.getProductionYear(),
                model.getId()) > 0;
    }

    @Override
    public boolean delete(Vehicle model) {
        return jdbcTemplate.update(DELETE, model.getId()) > 0;
    }

    @Override
    public List<Vehicle> selectAll() {
        return jdbcTemplate.query(SELECT_ALL, new VehicleRowMapper());
    }

    @Override
    public Vehicle select(String name) {
        return jdbcTemplate.queryForObject(SELECT_ONE_BY_NAME, new VehicleRowMapper(), name);
    }

    @Override
    public Vehicle select(UUID id) {
        return jdbcTemplate.queryForObject(SELECT_ONE_BY_ID, new VehicleRowMapper(), id);
    }

    private class VehicleRowMapper implements RowMapper<Vehicle> {
        @Override
        public Vehicle mapRow(ResultSet resultSet, int i) throws SQLException {
            UUID id = (UUID)resultSet.getObject("vehicle_id");
            String name = resultSet.getString("name");
            int power = resultSet.getInt("power");
            int productionYear = resultSet.getInt("production_year");
            return new Vehicle(id, name, power, productionYear);
        }
    }

}

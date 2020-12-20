package com.example.spedy.dao;

import com.example.spedy.model.Refueling;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.UUID;

@Repository("postgresRefuelingDao")
public class RefuelingDaoImp implements RefuelingDao{

    private final JdbcTemplate jdbcTemplate;
    private final static String INSERT_WITH_VEHICLE_ID = "INSERT INTO refueling(refuel_id, vehicle_id, amount, price_per_litre, " +
            "country, city, refuel_date) " +
            "VALUES(?, ?, ?, ?, ?, ?, ?)";
    private final static String INSERT_WITH_VEHICLE_NAME = "INSERT INTO refueling(refuel_id, vehicle_id, amount, price_per_litre, " +
            "country, city, refuel_date) " +
            "VALUES(?, (SELECT vehicle_id FROM vehicles WHERE name = ?), ?, ?, ?, ?, ?)";
    private final static String DELETE = "DELETE FROM refueling WHERE refuel_id = ?";
    private final static String UPDATE = "UPDATE refueling SET " +
            "amount = ?," +
            "price_per_litre = ?, " +
            "country = ?," +
            "city = ?, " +
            "refuel_date = ?" +
            "WHERE refuel_id = ?";
    private final static String SELECT_ALL = "SELECT * FROM refueling";
    private final static String SELECT_ALL_BY_VEHICLE_ID = "SELECT * FROM refueling WHERE vehicle_id =?";
    private final static String SELECT_ALL_BY_VEHICLE_NAME = "SELECT r.* FROM refueling r, vehicles v " +
            "WHERE r.vehicle_id = v.vehicle_id AND v.name = ?";
    private final static String SELECT_ONE_BY_ID = "SELECT * FROM refueling WHERE refuel_id = ?";


    @Autowired
    public RefuelingDaoImp(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public boolean insert(Refueling refueling, String vehicleName) {
        return jdbcTemplate.update(INSERT_WITH_VEHICLE_NAME,
                refueling.getId(),
                vehicleName,
                refueling.getAmount(),
                refueling.getPricePerLitre(),
                refueling.getCountry(),
                refueling.getCity(),
                refueling.getRefuelDate()) > 0;
    }

    @Override
    public boolean insert(Refueling refueling) {
        return jdbcTemplate.update(INSERT_WITH_VEHICLE_ID,
                refueling.getId(),
                refueling.getVehicleId(),
                refueling.getAmount(),
                refueling.getPricePerLitre(),
                refueling.getCountry(),
                refueling.getCity(),
                refueling.getRefuelDate()) > 0;
    }

    @Override
    public boolean update(Refueling refueling) {
        return jdbcTemplate.update(UPDATE,
                refueling.getAmount(),
                refueling.getPricePerLitre(),
                refueling.getCountry(),
                refueling.getCity(),
                refueling.getRefuelDate(),
                refueling.getId()) > 0;
    }

    @Override
    public boolean delete(Refueling refueling) {
        return jdbcTemplate.update(DELETE, refueling.getId()) > 0;
    }

    @Override
    public List<Refueling> selectAll() {
        return jdbcTemplate.query(SELECT_ALL, new RefuelingRowMapper());
    }

    @Override
    public List<Refueling> selectRefuelingForVehicle(String vehicleName) {
        return jdbcTemplate.query(SELECT_ALL_BY_VEHICLE_NAME, new RefuelingRowMapper(), vehicleName);
    }

    @Override
    public List<Refueling> selectRefuelingForVehicle(UUID vehicleId) {
        return jdbcTemplate.query(SELECT_ALL_BY_VEHICLE_ID, new RefuelingRowMapper(), vehicleId);
    }

    @Override
    public Refueling select(UUID id) {
        return jdbcTemplate.queryForObject(SELECT_ONE_BY_ID, new RefuelingRowMapper(), id);
    }

    @Override
    public Refueling select(Refueling refueling) {
        return select(refueling.getId());
    }

    private class RefuelingRowMapper implements RowMapper<Refueling> {
        @Override
        public Refueling mapRow(ResultSet resultSet, int i) throws SQLException {
            UUID id = (UUID)resultSet.getObject("refuel_id");
            UUID vehicleId = (UUID)resultSet.getObject("vehicle_id");
            int amount = resultSet.getInt("amount");
            double pricePerLitre = resultSet.getDouble("price_per_litre");
            String country = resultSet.getString("country");
            String city = resultSet.getString("city");
            Date refuelDate = resultSet.getDate("refuel_date");
            Refueling refueling = new Refueling(id, vehicleId, amount, pricePerLitre, country,
                    city, refuelDate);
            return refueling;
        }
    }

}

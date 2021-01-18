package com.example.spedy.dao;

import com.example.spedy.model.Accident;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.UUID;

@Repository("postgresAccidentDao")
public class AccidentDaoImp implements AccidentDao {

    private final JdbcTemplate jdbcTemplate;
    private static final String INSERT = "INSERT INTO accidents(accident_id, delivery_id, event_date, description) " +
            "VALUES (?, ?, ?, ?)";
    private static final String UPDATE = "UPDATE accidents SET " +
            "delivery_id = ?, " +
            "event_date = ?, " +
            "description = ? " +
            "WHERE accident_id = ?";
    private static final String DELETE = "DELETE FROM accidents " +
            "WHERE accident_id = ?";
    private static final String SELECT_ALL = "SELECT * FROM accidents";
    private static final String SELECT = "SELECT * FROM accidents " +
            "WHERE accident_id = ?";
    private static final String SELECT_ALL_WITH_DELIVERY_ID = "SELECT * FROM accidents " +
            "WHERE delivery_id = ?";


    @Autowired
    public AccidentDaoImp(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public boolean insert(Accident accident) {
        return jdbcTemplate.update(INSERT,
                accident.getAccidentId(),
                accident.getDeliveryId(),
                accident.getEventDate(),
                accident.getDescription()) > 0;
    }

    @Override
    public boolean update(Accident accident) {
        return jdbcTemplate.update(UPDATE,
                accident.getDeliveryId(),
                accident.getEventDate(),
                accident.getDescription(),
                accident.getAccidentId()) > 0;
    }

    @Override
    public boolean delete(Accident accident) {
        return jdbcTemplate.update(DELETE, accident.getAccidentId()) > 0;
    }

    @Override
    public Accident select(UUID accidentId) {
        return jdbcTemplate.queryForObject(SELECT, new AccidentRowMapper(), accidentId);
    }

    @Override
    public List<Accident> selectAll() {
        return jdbcTemplate.query(SELECT_ALL, new AccidentRowMapper());
    }

    @Override
    public List<Accident> selectAllForDeliveryId(UUID delivery_id) {
        return jdbcTemplate.query(SELECT_ALL_WITH_DELIVERY_ID, new AccidentRowMapper(), delivery_id);
    }

    private class AccidentRowMapper implements RowMapper<Accident>{
        @Override
        public Accident mapRow(ResultSet resultSet, int i) throws SQLException {
            UUID accidentId = (UUID)resultSet.getObject("accident_id");
            UUID deliveryId = (UUID)resultSet.getObject("delivery_id");
            Date eventDate = resultSet.getDate("event_date");
            String description = resultSet.getString("description");
            return new Accident(accidentId, deliveryId, eventDate, description);
        }
    }
}

package com.example.spedy.dao;


import com.example.spedy.model.Delivery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.UUID;

@Repository("postgresDeliveryDao")
public class DeliveryDaoImp implements DeliveryDao {

    private final JdbcTemplate jdbcTemplate;
    private final static String INSERT = "INSERT INTO " +
            "deliveries(delivery_id, employee_id, from_company_id, to_company_id, " +
                "vehicle_id, cargo_id, start_date, finish_date, is_finished) " +
            "VALUES( ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
    private final static String DELETE = "DELETE FROM deliveries " +
            "WHERE delivery_id = ?";
    private final static String UPDATE = "UPDATE deliveries " +
            "SET employee_id = ?, " +
            "from_company_id = ?, " +
            "to_company_id = ?, " +
            "vehicle_id = ?," +
            "weight = ?, " +
            "cargo_id = ?," +
            "start_date = ?, " +
            "finish_date = ?, " +
            "is_finished = ? " +
            "WHERE delivery_id = ?";
    private final static String SELECT_ALL = "SELECT * FROM deliveries "; //+ orderOption;
    private final static String SELECT_ALL_WITH_FROM_COMPANY_ID = "SELECT * FROM deliveries " +
                "WHERE from_company_id = ? "; // + orderOption;
    private final static String SELECT_ALL_WITH_TO_COMPANY_ID = "SELECT * FROM deliveries " +
                "WHERE to_company_id = ? ";// + orderOption;
    private final static String SELECT_ALL_UNFINISHED = "SELECT * FROM deliveries " +
                "WHERE is_finished = 'NO' "; // + orderOption;
    private final static String SELECT_ALL_FINISHED = "SELECT * FROM deliveries " +
            "WHERE is_finished = 'YES' "; // + orderOption;
    private final static String SELECT_ALL_STARTED_BEFORE = "SELECT * FROM deliveries " +
            "WHERE start_date < ? "; // + orderOption;
    private final static String SELECT_ALL_STARTED_AFTER = "SELECT * FROM deliveries " +
            "WHERE start_date > ?"; // + orderOption;
    private final static String SELECT_ALL_STARTED_BETWEEN_DATES = "SELECT * FROM deliveries " +
            "WHERE start_date > ? AND start_date < ?"; // + orderOption;
    private final static String SELECT_ALL_WITH_VEHICLE_ID = "SELECT * FROM deliveries " +
            "WHERE vehicle_id = ?";// +orderOption;
    private final static String SELECT = "SELECT * FROM deliveries " +
            "WHERE delivery_id = ?";

    @Autowired
    public DeliveryDaoImp(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public boolean insert(Delivery delivery) {
        return jdbcTemplate.update(INSERT,
                delivery.getDeliveryId(),
                delivery.getEmployeeId(),
                delivery.getFromCompanyId(),
                delivery.getToCompanyId(),
                delivery.getVehicleId(),
                delivery.getWeight(),
                delivery.getCargoId(),
                delivery.getStartDate(),
                delivery.getFinishDate(),
                delivery.getIsFinished()) > 0;
    }

    @Override
    public boolean update(Delivery delivery) {
        return jdbcTemplate.update(UPDATE,
                delivery.getEmployeeId(),
                delivery.getFromCompanyId(),
                delivery.getToCompanyId(),
                delivery.getVehicleId(),
                delivery.getWeight(),
                delivery.getCargoId(),
                delivery.getStartDate(),
                delivery.getFinishDate(),
                delivery.getIsFinished(),
                delivery.getDeliveryId()
                ) > 0;
    }

    @Override
    public boolean delete(Delivery delivery) {
        return jdbcTemplate.update(DELETE, delivery.getDeliveryId()) > 0;
    }

    @Override
    public Delivery select(UUID deliveryId) {
        return jdbcTemplate.queryForObject(SELECT, new DeliveryRowMapper(), deliveryId);
    }

    @Override
    public List<Delivery> selectAll(DeliveryOrderOptions option) {
        return jdbcTemplate.query(SELECT_ALL + option, new DeliveryRowMapper());
    }

    @Override
    public List<Delivery> selectWithFromCompanyId(UUID companyId, DeliveryOrderOptions option) {
        return jdbcTemplate.query(SELECT_ALL_WITH_FROM_COMPANY_ID + option,
                new DeliveryRowMapper(),
                companyId);
    }

    @Override
    public List<Delivery> selectWithToCompanyId(UUID companyId, DeliveryOrderOptions option) {
        return jdbcTemplate.query(SELECT_ALL_WITH_TO_COMPANY_ID + option,
                new DeliveryRowMapper(),
                companyId);
    }

    @Override
    public List<Delivery> selectUnfinished(DeliveryOrderOptions option) {
        return jdbcTemplate.query(SELECT_ALL_UNFINISHED + option,
                new DeliveryRowMapper());
    }

    @Override
    public List<Delivery> selectFinished(DeliveryOrderOptions option) {
        return jdbcTemplate.query(SELECT_ALL_FINISHED + option,
                new DeliveryRowMapper());
    }

    @Override
    public List<Delivery> selectStartedBefore(Date date, DeliveryOrderOptions option) {
        return jdbcTemplate.query(SELECT_ALL_STARTED_BEFORE + option,
                new DeliveryRowMapper(),
                date);
    }

    @Override
    public List<Delivery> selectStartedAfter(Date date, DeliveryOrderOptions option) {
        return jdbcTemplate.query(SELECT_ALL_STARTED_AFTER,
                new DeliveryRowMapper(),
                date);
    }

    @Override
    public List<Delivery> selectStartedBetweenDates(Date minDate, Date maxDate, DeliveryOrderOptions option) {
        return jdbcTemplate.query(SELECT_ALL_STARTED_BETWEEN_DATES,
                new DeliveryRowMapper(),
                minDate,
                maxDate);
    }

    @Override
    public List<Delivery> selectWithVehicleId(UUID vehicleId, DeliveryOrderOptions option) {
        return jdbcTemplate.query(SELECT_ALL_WITH_VEHICLE_ID,
                new DeliveryRowMapper(),
                vehicleId);
    }

    private class DeliveryRowMapper implements RowMapper<Delivery>{
        @Override
        public Delivery mapRow(ResultSet resultSet, int i) throws SQLException {
            UUID deliveryId = (UUID)resultSet.getObject("delivery_id");
            UUID employeeId = (UUID)resultSet.getObject("employee_id");
            UUID fromCompanyId = (UUID)resultSet.getObject("from_company_id");
            UUID toCompanyId = (UUID)resultSet.getObject("to_company_id");
            UUID vehicleId = (UUID)resultSet.getObject("vehicle_id");
            int weight = resultSet.getInt("weight");
            UUID cargoId = (UUID)resultSet.getObject("cargo_id");
            Date startDate = resultSet.getDate("start_date");
            Date finishDate = resultSet.getDate("finish_date");
            String isFinished = resultSet.getString("is_finished");
            return new Delivery(deliveryId,
                    employeeId,
                    fromCompanyId,
                    toCompanyId,
                    vehicleId,
                    weight,
                    cargoId,
                    startDate,
                    finishDate,
                    isFinished);
        }
    }
}

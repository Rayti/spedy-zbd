package com.example.spedy.dao.deliveryDao;


import com.example.spedy.model.deliveries.Delivery;
import com.example.spedy.model.deliveries.DeliveryBuilder;
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


    @Autowired
    public DeliveryDaoImp(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public boolean insert(Delivery delivery) {
        return jdbcTemplate.update(
                DeliveryQueries.getInsertQuery(),
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
        return jdbcTemplate.update(
                DeliveryQueries.getUpdateQuery(),
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
        return jdbcTemplate.update(
                DeliveryQueries.getDeleteQuery(),
                delivery.getDeliveryId()) > 0;
    }

    @Override
    public Delivery select(UUID deliveryId) {
        return jdbcTemplate.queryForObject(
                DeliveryQueries.getSelectQuery(),
                new DeliveryRowMapper(), deliveryId);
    }

    @Override
    public List<Delivery> selectAll(DeliveryOrderOptions option) {
        return jdbcTemplate.query(
                DeliveryQueries.getSelectAllQuery(option),
                new DeliveryRowMapper());
    }

    @Override
    public List<Delivery> selectWithFromCompanyId(UUID companyId, DeliveryOrderOptions option) {
        return jdbcTemplate.query(
                DeliveryQueries.getSelectAllWithFromCompanyIdQuery(option),
                new DeliveryRowMapper(),
                companyId);
    }

    @Override
    public List<Delivery> selectWithToCompanyId(UUID companyId, DeliveryOrderOptions option) {
        return jdbcTemplate.query(
                DeliveryQueries.getSelectAllWithToCompanyIdQuery(option),
                new DeliveryRowMapper(),
                companyId);
    }

    @Override
    public List<Delivery> selectUnfinished(DeliveryOrderOptions option) {
        return jdbcTemplate.query(
                DeliveryQueries.getSelectAllUnfinishedQuery(option),
                new DeliveryRowMapper());
    }

    @Override
    public List<Delivery> selectFinished(DeliveryOrderOptions option) {
        return jdbcTemplate.query(
                DeliveryQueries.getSelectAllFinishedQuery(option),
                new DeliveryRowMapper());
    }

    @Override
    public List<Delivery> selectStartedBefore(Date date, DeliveryOrderOptions option) {
        return jdbcTemplate.query(
                DeliveryQueries.getSelectAllStartedBeforeQuery(option),
                new DeliveryRowMapper(),
                date);
    }

    @Override
    public List<Delivery> selectStartedAfter(Date date, DeliveryOrderOptions option) {
        return jdbcTemplate.query(
                DeliveryQueries.getSelectAllStartedAfterQuery(option),
                new DeliveryRowMapper(),
                date);
    }

    @Override
    public List<Delivery> selectStartedBetweenDates(Date minDate, Date maxDate, DeliveryOrderOptions option) {
        return jdbcTemplate.query(
                DeliveryQueries.getSelectAllStartedBetweenDatesQuery(option),
                new DeliveryRowMapper(),
                minDate,
                maxDate);
    }

    @Override
    public List<Delivery> selectWithVehicleId(UUID vehicleId, DeliveryOrderOptions option) {
        return jdbcTemplate.query(
                DeliveryQueries.getSelectAllWithVehicleIdQuery(option),
                new DeliveryRowMapper(),
                vehicleId);
    }

    private class DeliveryRowMapper implements RowMapper<Delivery> {
        @Override
        public Delivery mapRow(ResultSet resultSet, int i) throws SQLException {
            Date startDate = resultSet.getDate("start_date");
            Date finishDate = resultSet.getDate("finish_date");
            String isFinished = resultSet.getString("is_finished");
            return DeliveryBuilder.begin()
                    .withObligatoryIds(
                            (UUID)resultSet.getObject("delivery_id"),
                            (UUID)resultSet.getObject("employee_id"),
                            (UUID)resultSet.getObject("from_company_id"),
                            (UUID)resultSet.getObject("to_company_id"),
                            (UUID)resultSet.getObject("vehicle_id"),
                            (UUID)resultSet.getObject("cargo_id"))
                    .withObligatoryWeight(
                            resultSet.getInt("weight"))
                    .withObligatoryDates(
                            resultSet.getDate("start_date"),
                            resultSet.getDate("finish_date"),
                            resultSet.getString("is_finished"))
                    .withEmployeeData(
                            resultSet.getString("employee_first_name"),
                            resultSet.getString("employee_last_name"))
                    .withCompanyData(
                            resultSet.getString("from_company_name"),
                            resultSet.getString("to_company_name"))
                    .withVehicleData(
                            resultSet.getString("vehicle_name"))
                    .withCargoData(
                            resultSet.getString("cargo_name"))
                    .get();
        }
    }
}
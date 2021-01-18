package com.example.spedy.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository("postgresFunctionsDao")
public class FunctionsDaoImp implements FunctionsDao {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public FunctionsDaoImp(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public int getNumberOfComplaintsForEmployee(UUID employeeId) {
        return jdbcTemplate.queryForObject(
                "SELECT get_complaint_count(?)",
                (resultSet, i) -> resultSet.getInt(1), employeeId );
    }

    @Override
    public int getNumberOfDeliveriesForEmployee(UUID employeeId) {
        return jdbcTemplate.queryForObject(
                "SELECT get_delivery_count(?)",
                (resultSet, i) -> resultSet.getInt(1), employeeId);
    }

    @Override
    public void increaseProfessionsPayRange(float value) {
        jdbcTemplate.execute("call increase_profession_pay_range_procedure(" + value +")");
    }
}

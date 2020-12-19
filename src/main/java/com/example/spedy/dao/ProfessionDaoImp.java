package com.example.spedy.dao;

import com.example.spedy.model.Profession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.UUID;

@Repository("postgresProfessionDao")
public class ProfessionDaoImp implements SimpleDao<Profession> {

    private final JdbcTemplate jdbcTemplate;
    private static final String INSERT = "INSERT INTO professions(profession_id, min_salary, max_salary, title) " +
            "VALUES (?, ?, ?, ?)";
    private static final String DELETE = "DELETE FROM professions WHERE profession_id = ?";
    private static final String UPDATE = "UPDATE professions SET min_salary = ?, max_salary = ?, title = ?" +
            "WHERE profession_id = ?";
    private static final String SELECT_ALL = "SELECT * FROM professions";
    private static final String SELECT_ONE_BY_ID = "SELECT * FROM professions WHERE profession_id = ?";
    private static final String SELECT_ONE_BY_TITLE = "SELECT * FROM professions WHERE title = ?";


    @Autowired
    public ProfessionDaoImp(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public boolean insert(Profession profession) {
        return jdbcTemplate.update(INSERT,
                profession.getProfessionId(),
                profession.getMinSalary(),
                profession.getMaxSalary(),

                profession.getTitle()) > 0;
    }

    @Override
    public boolean delete(Profession profession) {
        return jdbcTemplate.update(DELETE, profession.getProfessionId()) > 0;
    }

    @Override
    public boolean update(Profession profession) {
        return jdbcTemplate.update(UPDATE,
                profession.getMinSalary(),
                profession.getMaxSalary(),
                profession.getTitle(),
                profession.getProfessionId()) > 0;
    }

    @Override
    public List<Profession> selectAll() {
        return jdbcTemplate.query(SELECT_ALL, new ProfessionRowMapper());
    }

    @Override
    public Profession select(String title) {
        try {
            return jdbcTemplate.queryForObject(SELECT_ONE_BY_TITLE, new ProfessionRowMapper(), title);
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public Profession select(UUID id) {
        return jdbcTemplate.queryForObject(SELECT_ONE_BY_ID, new ProfessionRowMapper(), id);
    }

    @Override
    public Profession select(Profession profession) {
        return select(profession.getProfessionId());
    }

    private class ProfessionRowMapper implements RowMapper<Profession>{

        @Override
        public Profession mapRow(ResultSet resultSet, int i) throws SQLException {
            UUID profession_id = (UUID)resultSet.getObject("profession_id");
            int minSalary = resultSet.getInt("min_salary");
            int maxSalary = resultSet.getInt("max_salary");
            String title = resultSet.getString("title");
            return new Profession(profession_id,
                    minSalary,
                    maxSalary,
                    title);
        }
    }
}

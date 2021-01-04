package com.example.spedy.dao;

import com.example.spedy.model.Employee;
import com.example.spedy.model.Profession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.UUID;

@Repository("postgresEmployeeDao")
public class EmployeeDaoImp implements EmployeeDao {

    private final JdbcTemplate jdbcTemplate;
    private final static String INSERT_WITH_IDS = "INSERT INTO " +
            "employees(employee_id, first_name, last_name, salary, profession_id, user_id) " +
            "VALUES(?, ?, ?, ?, ?, ?)";
    private final static String INSERT_WITH_TITLE_AND_USER_ID = "INSERT INTO " +
            "employees(employee_id, first_name, last_name, salary, profession_id, user_id) " +
            "VALUES(?, ?, ?, ?, " +
                "(SELECT profession_id FROM professions WHERE title = ?), " +
                "?)";
    private final static String DELETE = "DELETE FROM employees WHERE employee_id = ?";
    private final static String UPDATE = "UPDATE employees SET " +
            "first_name = ?, " +
            "last_name = ?, " +
            "salary = ?, " +
            "profession_id = ?, " +
            "user_id = ? " +
            "WHERE employee_id = ?";
    private final static String SELECT_ALL = "SELECT * FROM employees";
    private final static String SELECT_ALL_WITH_PROFESSION_TITLE = "SELECT * " +
            "FROM employees WHERE profession_id = " +
                "(SELECT profession_id FROM professions WHERE title = ?)";
    private final static String SELECT_WITH_ITS_ID = "SELECT * FROM employees " +
            "WHERE employee_id = ?";
    private final static String SELECT_WITH_USER_ID = "SELECT * FROM employees " +
            "WHERE user_id = ?";
    private final static String SELECT_WITH_USERNAME = "SELECT * FROM employees " +
            "WHERE user_id = (SELECT user_id FROM app_users WHERE login = ?)";

    @Autowired
    public EmployeeDaoImp(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public boolean insert(Employee employee) {
        return jdbcTemplate.update(INSERT_WITH_IDS,
                employee.getEmployeeId(),
                employee.getFirstName(),
                employee.getLastName(),
                employee.getSalary(),
                employee.getProfessionId(),
                employee.getUserId()) > 0;
    }

    @Override
    public boolean update(Employee employee) {
        return jdbcTemplate.update(UPDATE,
                employee.getFirstName(),
                employee.getLastName(),
                employee.getSalary(),
                employee.getProfessionId(),
                employee.getUserId(),
                employee.getEmployeeId()) > 0;
    }

    @Override
    public boolean delete(Employee employee) {
        return jdbcTemplate.update(DELETE, employee.getEmployeeId()) > 0;
    }

    @Override
    public List<Employee> selectAll() {
        return jdbcTemplate.query(SELECT_ALL, new EmployeeRowMapper());
    }

    @Override
    public List<Employee> selectAllWithProfession(String professionTitle) {
        return jdbcTemplate.query(SELECT_ALL_WITH_PROFESSION_TITLE, new EmployeeRowMapper(), professionTitle);
    }


    @Override
    public Employee select(UUID id) {
        return jdbcTemplate.queryForObject(SELECT_WITH_ITS_ID, new EmployeeRowMapper(), id);
    }

    @Override
    public Employee selectWithUserId(UUID userId) {
        return jdbcTemplate.queryForObject(SELECT_WITH_USER_ID, new EmployeeRowMapper(), userId);
    }

    @Override
    public Employee selectWithUserName(String username) {
        return jdbcTemplate.queryForObject(SELECT_WITH_USERNAME, new EmployeeRowMapper(), username);
    }

    private class EmployeeRowMapper implements RowMapper<Employee>{
        @Override
        public Employee mapRow(ResultSet resultSet, int i) throws SQLException {
            UUID employeeId = (UUID)resultSet.getObject("employee_id");
            String firstName = resultSet.getString("first_name");
            String lastName = resultSet.getString("last_name");
            int salary = resultSet.getInt("salary");
            UUID professionId = (UUID)resultSet.getObject("profession_id");
            UUID user_id = (UUID)resultSet.getObject("user_id");
            return new Employee(
                    employeeId,
                    professionId,
                    user_id,
                    firstName,
                    lastName,
                    salary);
        }
    }
}

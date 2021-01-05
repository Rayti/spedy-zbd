package com.example.spedy.dao;

import com.example.spedy.model.Complaint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.UUID;

@Repository("postgresComplaintDao")
public class ComplaintDaoImp implements SimpleDao<Complaint> {

    private final JdbcTemplate jdbcTemplate;
    private final static String INSERT = "INSERT INTO complaints" +
            "(complaint_id, employee_id, company_id, description) " +
            "VALUES " +
            "(?, ?, ?, ?)";
    private final static String DELETE = "DELETE FROM complaints " +
            "WHERE complaint_id = ?";
    private final static String UPDATE = "UPDATE complaints " +
            "SET employee_id = ?, " +
            "company_id = ?, " +
            "description = ? " +
            "WHERE complaint_id = ?";
    private final static String SELECT_ALL = "SELECT * FROM complaints";
    private final static String SELECT_BY_ID = "SELECT * FROM complaints " +
            "WHERE complaint_id = ?";
    private final static String SELECT_BY_COMPANY_NAME = "SELECT c1.* FROM complaints c1, companies c2 " +
            "WHERE c1.company_id = c2.company_id AND c2.name = ?";


    @Autowired
    public ComplaintDaoImp(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public boolean insert(Complaint complaint) {
        return jdbcTemplate.update(INSERT, complaint.getComplaintId(),
                complaint.getEmployeeId(),
                complaint.getCompanyId(),
                complaint.getDescription()) > 0;
    }

    @Override
    public boolean update(Complaint complaint) {
        return jdbcTemplate.update(UPDATE, complaint.getEmployeeId(),
                complaint.getCompanyId(),
                complaint.getDescription(),
                complaint.getComplaintId()) > 0;
    }

    @Override
    public boolean delete(Complaint complaint) {
        return jdbcTemplate.update(DELETE, complaint.getComplaintId()) > 0 ;
    }

    @Override
    public List<Complaint> selectAll() {
        return jdbcTemplate.query(SELECT_ALL, new ComplaintRowMapper());

    }

    @Override
    public Complaint select(String companyName) {
        return jdbcTemplate.queryForObject(SELECT_BY_COMPANY_NAME, new ComplaintRowMapper(), companyName);
    }

    @Override
    public Complaint select(UUID id) {
        return jdbcTemplate.queryForObject(SELECT_BY_ID, new ComplaintRowMapper(), id);
    }

    private class ComplaintRowMapper implements RowMapper<Complaint> {
        @Override
        public Complaint mapRow(ResultSet resultSet, int i) throws SQLException {
            UUID complaintID = (UUID)resultSet.getObject("complaint_id");
            UUID employeeId = (UUID)resultSet.getObject("employee_id");
            UUID companyId = (UUID)resultSet.getObject("company_id");
            String description = resultSet.getString("description");
            return new Complaint(complaintID, employeeId, companyId, description);
        }
    }

}

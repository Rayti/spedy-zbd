package com.example.spedy.service;

import com.example.spedy.dao.SimpleDao;
import com.example.spedy.model.Complaint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service("complaintService")
public class ComplaintService {

    private final SimpleDao<Complaint> dao;

    @Autowired
    public ComplaintService(@Qualifier("postgresComplaintDao") SimpleDao<Complaint> dao) {
        this.dao = dao;
    }

    public Complaint getComplaint(UUID id) {
        return dao.select(id);
    }

    public Complaint getComplaint(String companyName) {
        return dao.select(companyName);
    }

    public List<Complaint> getAll() {
        return dao.selectAll();
    }

    public String deleteComplaint(Complaint complaint) {
        String responseIfTrue = "Complaint deleted from database.";
        String responseIfFalse = "Could not delete complaint. Check spelling.";
        return dao.delete(complaint) ? responseIfTrue : responseIfFalse;
    }

    public String insertComplaint(Complaint complaint) {
        String responseIfTrue = "Complaint added to database.";
        String responseIfFalse = "Could not add complaint. Check spelling.";
        return dao.insert(complaint) ? responseIfTrue : responseIfFalse;
    }

    public String updateComplaint(Complaint complaint) {
        String responseIfTrue = "Complaint updated.";
        String responseIfFalse = "Could not update complaint. Check spelling.";
        return dao.update(complaint) ? responseIfTrue : responseIfFalse;
    }
}

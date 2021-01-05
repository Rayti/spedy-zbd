package com.example.spedy.model;

import java.util.UUID;

public class Complaint {

    private final UUID complaintId;
    private UUID employeeId;
    private UUID companyId;
    private String description;

    public Complaint(UUID complaintId, UUID employeeId, UUID companyId, String description) {
        this.complaintId = complaintId;
        this.employeeId = employeeId;
        this.companyId = companyId;
        this.description = description;
    }

    public Complaint(UUID employeeId, UUID companyId, String description) {
        this(UUID.randomUUID(), employeeId, companyId, description);
    }

    public UUID getComplaintId() {
        return complaintId;
    }

    public UUID getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(UUID employeeId) {
        this.employeeId = employeeId;
    }

    public UUID getCompanyId() {
        return companyId;
    }

    public void setCompanyId(UUID companyId) {
        this.companyId = companyId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}

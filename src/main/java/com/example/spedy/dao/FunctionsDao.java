package com.example.spedy.dao;

import java.util.UUID;

public interface FunctionsDao {

    int getNumberOfComplaintsForEmployee(UUID employeeId);

    int getNumberOfDeliveriesForEmployee(UUID employeeId);

    void increaseProfessionsPayRange(float value);

}

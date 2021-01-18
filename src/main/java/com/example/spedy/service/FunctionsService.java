package com.example.spedy.service;

import com.example.spedy.dao.FunctionsDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service("functionsService")
public class FunctionsService {

    private final FunctionsDao dao;

    @Autowired
    public FunctionsService(@Qualifier("postgresFunctionsDao") FunctionsDao dao) {
        this.dao = dao;
    }

    public int getNumberOfComplaintsForEmployee(UUID employeeId) {
        return dao.getNumberOfComplaintsForEmployee(employeeId);
    }

    public int getNumberOfDeliveriesForEmployee(UUID employeeId){
        return dao.getNumberOfDeliveriesForEmployee(employeeId);
    }

    public void increaseAllProfessionsPayRange(float value) {
        dao.increaseProfessionsPayRange(value);
    }
}

package com.example.spedy.service;

import com.example.spedy.dao.RefuelingDao;
import com.example.spedy.model.Refueling;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service("refuelingService")
public class RefuelingService {

    private final RefuelingDao dao;

    @Autowired
    public RefuelingService(@Qualifier("postgresRefuelingDao") RefuelingDao dao) {
        this.dao = dao;
    }

    public Refueling getRefueling(Refueling refueling) {
        return dao.select(refueling);
    }

    public Refueling getRefueling(UUID id) {
        return dao.select(id);
    }

    public List<Refueling> getAll() {
        return dao.selectAll();
    }

    public List<Refueling> getAllForVehicle(String vehicleName) {
        return dao.selectRefuelingForVehicle(vehicleName);
    }

    public List<Refueling> getAllForVehicle(UUID vehicleId) {
        return dao.selectRefuelingForVehicle(vehicleId);
    }

    public String deleteRefueling(Refueling refueling) {
        String responseIfTrue = "Refueling data deleted from database.";
        String responseIfFalse = "Could not delete refueling. Check spelling.";
        return dao.delete(refueling) ? responseIfTrue : responseIfFalse;
    }

    public String insertRefueling(Refueling refueling, String vehicleName) {
        String responseIfTrue = "Refueling added to database.";
        String responseIfFalse = "Could not add refueling. Check spelling.";
        return dao.insert(refueling, vehicleName) ? responseIfTrue : responseIfFalse;
    }

    public String insertRefueling(Refueling refueling) {
        String responseIfTrue = "Refueling added to database.";
        String responseIfFalse = "Could not add refueling. Check spelling.";
        return dao.insert(refueling) ? responseIfTrue : responseIfFalse;
    }

    public String updateRefueling(Refueling refueling) {
        String responseIfTrue = "Refueling updated.";
        String responseIfFalse = "Could not update. Check spelling.";
        return dao.update(refueling) ? responseIfTrue : responseIfFalse;
    }

}

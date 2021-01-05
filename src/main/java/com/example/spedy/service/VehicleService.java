package com.example.spedy.service;

import com.example.spedy.dao.SimpleDao;
import com.example.spedy.model.Vehicle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service("vehicleService")
public class VehicleService {

    private final SimpleDao<Vehicle> dao;

    @Autowired
    public VehicleService(@Qualifier("postgresVehicleDao") SimpleDao<Vehicle> dao) {
        this.dao = dao;
    }

    public Vehicle getVehicle(UUID id) {
        return dao.select(id);
    }

    public Vehicle getVehicle(String name){
        return dao.select(name);
    }

    public List<Vehicle> getVehicles() {
        return dao.selectAll();
    }

    public String deleteVehicle(Vehicle vehicle) {
        String responseIfTrue = "Vehicle " + vehicle.getName() + " deleted from database.";
        String responseIfFalse = "Could not delete vehicle. Check spelling.";
        return dao.delete(vehicle) ? responseIfTrue : responseIfFalse;
    }

    public String updateVehicle(Vehicle vehicle) {
        if (vehicleExistsAlready(vehicle)) {
            return "Vehicle with this name already exists.";
        }
        String responseIfTrue = "Vehicle " + vehicle.getName() + " updated.";
        String responseIfFalse = "Could not update. Check spelling.";
        return dao.update(vehicle) ? responseIfTrue : responseIfFalse;
    }

    public String insertVehicle(Vehicle vehicle) {
        if (vehicleExistsAlready(vehicle)) {
            return "Vehicle with this name already exists.";
        }
        String responseIfTrue = "Vehicle " + vehicle.getName() + " added to database.";
        String responseIfFalse = "Could not add new Vehicle. Check spelling.";
        return dao.insert(vehicle) ? responseIfTrue : responseIfFalse;
    }

    private boolean vehicleExistsAlready(Vehicle vehicle) {
        return dao.selectAll().stream()
                .anyMatch(v -> v.getName().equals(vehicle.getName())
                    && v.getId().compareTo(vehicle.getId()) != 0 );
    }
}

package com.example.spedy.dao;

import com.example.spedy.model.Refueling;

import java.util.List;
import java.util.UUID;

public interface RefuelingDao {
    boolean insert(Refueling refueling, String vehicleName);

    boolean insert(Refueling refueling);

    boolean update(Refueling refueling);

    boolean delete(Refueling refueling);

    List<Refueling> selectAll();

    List<Refueling> selectRefuelingForVehicle(String vehicleName);

    List<Refueling> selectRefuelingForVehicle(UUID vehicleId);

    Refueling select(Refueling refueling);

    Refueling select(UUID id);
}

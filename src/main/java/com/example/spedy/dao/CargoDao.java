package com.example.spedy.dao;

import com.example.spedy.model.Cargo;

import java.util.List;
import java.util.UUID;

public interface CargoDao {

    boolean insertCargo(Cargo cargo);

    boolean deleteCargo(Cargo cargo);

    boolean updateCargo(Cargo cargo);

    List<Cargo> selectCargos();

    Cargo selectCargo(UUID id);

    Cargo selectCargo(String name);

    default Cargo selectCargo(Cargo cargo) {
        return selectCargo(cargo.getId());
    }
}

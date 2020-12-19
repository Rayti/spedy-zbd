package com.example.spedy.service;

import com.example.spedy.dao.CargoDao;
import com.example.spedy.model.Cargo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service("cargoService")
public class CargoService {

    private final CargoDao cargoDao;

    @Autowired
    public CargoService(@Qualifier("postgresCargoDao") CargoDao cargoDao) {
        this.cargoDao = cargoDao;
    }


    public Cargo getCargo(Cargo cargo) {
        return cargoDao.selectCargo(cargo);
    }

    public Cargo getCargo(String name) {
        return cargoDao.selectCargo(name);
    }

    public Cargo getCargo(UUID id) {
        return cargoDao.selectCargo(id);
    }

    public List<Cargo> getCargos() {
        return cargoDao.selectCargos();
    }

    public String deleteCargo(Cargo cargo){
        String responseIfTrue = "Cargo " + cargo.getName() + " deleted from database.";
        String responseIfFalse = "Could not delete cargo. Check spelling.";
        return cargoDao.deleteCargo(cargo) ? responseIfTrue : responseIfFalse;
    }

    public String insertCargo(Cargo cargo) {
        if (cargoExistsAlready(cargo)) {
            return "Cargo with this name already exists.";
        }
        String responseIfTrue = "Cargo " + cargo.getName() + " added to database.";
        String responseIfFalse = "Could not add new Cargo. Check spelling.";
        return cargoDao.insertCargo(cargo) ? responseIfTrue : responseIfFalse;
    }


    public String updateCargo(Cargo cargo) {
        if (cargoExistsAlready(cargo)) {
            return "Cargo with this title already exists.";
        }
        String responseIfTrue = "Cargo " + cargo.getName() + " updated.";
        String responseIfFalse = " Could not update new Cargo. Check spelling.";
        return cargoDao.updateCargo(cargo) ? responseIfTrue : responseIfFalse;
    }

    private boolean cargoExistsAlready(Cargo cargo) {
        return cargoDao.selectCargos().stream()
                .anyMatch(c -> c.getName().equals(cargo.getName())
                    && c.getId().compareTo(cargo.getId()) != 0 );
    }

}

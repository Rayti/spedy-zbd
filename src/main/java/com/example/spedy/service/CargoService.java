package com.example.spedy.service;

import com.example.spedy.dao.SimpleDao;
import com.example.spedy.model.Cargo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service("cargoService")
public class CargoService {

    private final SimpleDao<Cargo> dao;

    @Autowired
    public CargoService(@Qualifier("postgresCargoDao") SimpleDao<Cargo> dao) {
        this.dao = dao;
    }


    public Cargo getCargo(String name) {
        return dao.select(name);
    }

    public Cargo getCargo(UUID id) {
        return dao.select(id);
    }

    public List<Cargo> getCargos() {
        return dao.selectAll();
    }

    public String deleteCargo(Cargo cargo){
        String responseIfTrue = "Cargo " + cargo.getName() + " deleted from database.";
        String responseIfFalse = "Could not delete cargo. Check spelling.";
        return dao.delete(cargo) ? responseIfTrue : responseIfFalse;
    }

    public String insertCargo(Cargo cargo) {
        if (cargoExistsAlready(cargo)) {
            return "Cargo with this name already exists.";
        }
        String responseIfTrue = "Cargo " + cargo.getName() + " added to database.";
        String responseIfFalse = "Could not add new Cargo. Check spelling.";
        return dao.insert(cargo) ? responseIfTrue : responseIfFalse;
    }


    public String updateCargo(Cargo cargo) {
        if (cargoExistsAlready(cargo)) {
            return "Cargo with this title already exists.";
        }
        String responseIfTrue = "Cargo " + cargo.getName() + " updated.";
        String responseIfFalse = " Could not update new Cargo. Check spelling.";
        return dao.update(cargo) ? responseIfTrue : responseIfFalse;
    }

    private boolean cargoExistsAlready(Cargo cargo) {
        return dao.selectAll().stream()
                .anyMatch(c -> c.getName().equals(cargo.getName())
                    && c.getId().compareTo(cargo.getId()) != 0 );
    }

}

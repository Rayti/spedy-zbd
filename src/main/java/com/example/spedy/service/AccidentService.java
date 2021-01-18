package com.example.spedy.service;

import com.example.spedy.dao.AccidentDao;
import com.example.spedy.model.Accident;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service("accidentService")
public class AccidentService {

    private final AccidentDao dao;

    @Autowired
    public AccidentService(@Qualifier("postgresAccidentDao") AccidentDao dao) {
        this.dao = dao;
    }

    public Accident getAccident(UUID accidentId) {
        return dao.select(accidentId);
    }

    public List<Accident> getAll() {
        return dao.selectAll();
    }

    public List<Accident> getAllWithDeliveryId(UUID deliveryId) {
        return dao.selectAllForDeliveryId(deliveryId);
    }

    public String insertAccident(Accident accident) {
        String responseIfTrue = "Accident added.";
        String responseIfFalse = "Could not insert accident. Check spelling.";
        return dao.insert(accident) ? responseIfTrue : responseIfFalse;
    }

    public String deleteAccident(Accident accident){
        String responseIfTrue = "Accident deleted from database.";
        String responseIfFalse = "Could not delete accident. Check spelling.";
        return dao.delete(accident) ? responseIfTrue : responseIfFalse;
    }

    public String updateAccident(Accident accident) {
        String responseIfTrue = "Accident updated.";
        String responseIfFalse = "Could not update accident. Check spelling.";
        return dao.update(accident) ? responseIfTrue : responseIfFalse;
    }
}

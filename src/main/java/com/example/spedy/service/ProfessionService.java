package com.example.spedy.service;


import com.example.spedy.dao.SimpleDao;
import com.example.spedy.model.Profession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service("professionService")
public class ProfessionService {

    private final SimpleDao<Profession> dao;

    @Autowired
    public ProfessionService(@Qualifier("postgresProfessionDao") SimpleDao<Profession> dao) {
        this.dao = dao;
    }

    public Profession getProfession(String title) {
        return dao.select(title);
    }

    public Profession getProfession(UUID id) {
        return dao.select(id);
    }

    public List<Profession> getProfessions() {
        return dao.selectAll();
    }

    public String deleteProfession(Profession profession){
        String responseIfTrue = "Profession " + profession.getTitle() + " deleted from database.";
        String responseIfFalse = "Could not delete profession. Check spelling.";
        return dao.delete(profession) ? responseIfTrue : responseIfFalse;
    }

    public String insertProfession(Profession profession) {
        if (professionExistsAlready(profession)) {
            return "Profession with this title already exists.";
        }
        String responseIfTrue = "Profession " + profession.getTitle() + " added to database.";
        String responseIfFalse = "Could not add new Profession. Check spelling.";
        return dao.insert(profession) ? responseIfTrue : responseIfFalse;
    }

    public String updateProfession(Profession profession) {
        if (professionExistsAlready(profession)) {
            return "Profession with this title already exists.";
        }
        String responseIfTrue = "Profession " + profession.getTitle() + " updated.";
        String responseIfFalse = " Could not update new Profession. Check spelling.";
        return dao.update(profession) ? responseIfTrue : responseIfFalse;
    }

    private boolean professionExistsAlready(Profession profession) {
        return dao.selectAll().stream()
                .anyMatch(daoProf -> daoProf.getTitle().equals(profession.getTitle())
                        && daoProf.getProfessionId().compareTo(profession.getProfessionId()) != 0);
    }
}

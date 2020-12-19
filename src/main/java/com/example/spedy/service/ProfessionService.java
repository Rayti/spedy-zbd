package com.example.spedy.service;


import com.example.spedy.dao.ProfessionDao;
import com.example.spedy.model.Profession;
import com.example.spedy.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service("professionService")
public class ProfessionService {

    private final ProfessionDao professionDao;

    @Autowired
    public ProfessionService(@Qualifier("postgresProfessionDao") ProfessionDao professionDao) {
        this.professionDao = professionDao;
    }

    public Profession getProfession(Profession profession) {
        return professionDao.selectProfession(profession);
    }

    public Profession getProfession(String title) {
        return professionDao.selectProfession(title);
    }

    public Profession getProfession(UUID id) {
        return professionDao.selectProfession(id);
    }

    public List<Profession> getProfessions() {
        return professionDao.selectProfessions();
    }

    public String deleteProfession(Profession profession){
        String responseIfTrue = "Profession " + profession.getTitle() + " deleted from database.";
        String responseIfFalse = "Could not delete profession. Check spelling.";
        return professionDao.deleteProfession(profession) ? responseIfTrue : responseIfFalse;
    }

    public String insertProfession(Profession profession) {
        if (professionExistsAlready(profession)) {
            return "Profession with this title already exists.";
        }
        String responseIfTrue = "Profession " + profession.getTitle() + " added to database.";
        String responseIfFalse = "Could not add new Profession. Check spelling.";
        return professionDao.insertProfession(profession) ? responseIfTrue : responseIfFalse;

    }

    public String updateProfession(Profession profession) {
        if (professionExistsAlready(profession)) {
            return "Profession with this title already exists.";
        }
        String responseIfTrue = "Profession " + profession.getTitle() + " updated.";
        String responseIfFalse = " Could not update new Profession. Check spelling.";
        return professionDao.updateProfession(profession) ? responseIfTrue : responseIfFalse;
    }

    private boolean professionExistsAlready(Profession profession) {
        return professionDao.selectProfessions().stream()
                .anyMatch(daoProf -> daoProf.getTitle().equals(profession.getTitle())
                        && daoProf.getProfessionId().compareTo(profession.getProfessionId()) != 0);
    }
}

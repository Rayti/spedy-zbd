package com.example.spedy.dao;

import com.example.spedy.model.Profession;

import java.util.List;
import java.util.UUID;

public interface ProfessionDao {

    boolean insertProfession(Profession profession);

    boolean deleteProfession(Profession profession);

    boolean updateProfession(Profession profession);

    List<Profession> selectProfessions();

    Profession selectProfession(Profession profession);

    Profession selectProfession(String title);

    Profession selectProfession(UUID id);
}

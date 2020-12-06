package com.example.spedy.dao;

import com.example.spedy.model.Person;

import java.util.List;
import java.util.UUID;

public interface PersonDao {

    boolean insertPerson(UUID id, Person person);

    default boolean insertPerson(Person person) {
        UUID id = UUID.randomUUID();
        return insertPerson(id, person);
    }

    List<Person> selectAllPeople();

    boolean deletePerson(UUID id, Person person);

}

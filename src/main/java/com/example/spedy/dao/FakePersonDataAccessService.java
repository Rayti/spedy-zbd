package com.example.spedy.dao;

import com.example.spedy.model.Person;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


@Repository("fakePerson")
public class FakePersonDataAccessService implements PersonDao {

    private static List<Person> DB = new ArrayList<>();
    @Override
    public boolean insertPerson(UUID id, Person person) {
        DB.add(new Person(id, person.getName()));
        return true;
    }

    @Override
    public List<Person> selectAllPeople() {
        return DB;
    }

    @Override
    public boolean deletePerson(UUID id, Person person) {
        return DB.removeIf(x -> x.getId().equals(id) && x.getName().equals(person.getName()));
    }
}

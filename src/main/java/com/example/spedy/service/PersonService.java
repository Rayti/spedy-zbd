package com.example.spedy.service;

import com.example.spedy.dao.PersonDao;
import com.example.spedy.model.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;


@Service
public class PersonService {

    private final PersonDao personDao;

    @Autowired
    public PersonService(@Qualifier("postgres") PersonDao personDao) {
        this.personDao = personDao;
    }

    public boolean addPerson(Person person) {
        return personDao.insertPerson(person);
    }

    public List<Person> getAllPeople() {
        return personDao.selectAllPeople();
    }

    public boolean deletePerson(UUID id, Person person) {
        return personDao.deletePerson(id, person);
    }

    public boolean changePersonDescription(Person person, String description) {
        personDao.deletePerson(person.getId(), person);
        person.setDescription(description);
        personDao.insertPerson(person.getId(), person);
        return true;
    }
}

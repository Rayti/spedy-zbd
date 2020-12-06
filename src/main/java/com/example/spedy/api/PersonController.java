package com.example.spedy.api;

import com.example.spedy.model.Person;
import com.example.spedy.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RequestMapping("/api/v1/person")
@RestController
public class PersonController {

    private final PersonService personService;

    @Autowired
    public PersonController(PersonService personService) {
        this.personService = personService;
    }

    @PostMapping
    public void addPerson(@RequestBody  Person person) {
        personService.addPerson(person);

    }

    @GetMapping
    public List<Person> getAllPeople(){
        return personService.getAllPeople();
    }

    @PutMapping
    public void deletePerson(@RequestBody Person person) {
        personService.deletePerson(person.getId(), person);
    }

}
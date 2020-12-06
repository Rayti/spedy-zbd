package com.example.spedy.api;

import com.example.spedy.model.Person;
import com.example.spedy.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
/*


@RequestMapping("/api/v1/person")
@RestController
public class PersonController {

    private final UserService userService;

    @Autowired
    public PersonController(UserService personService) {
        this.userService = personService;
    }

    @PostMapping
    public void addPerson(@RequestBody  Person person) {
        userService.addPerson(person);

    }

    @GetMapping
    public List<Person> getAllPeople(){
        return userService.getAllPeople();
    }

    @PutMapping
    public void deletePerson(@RequestBody Person person) {
        userService.deletePerson(person.getId(), person);
    }

}
*/

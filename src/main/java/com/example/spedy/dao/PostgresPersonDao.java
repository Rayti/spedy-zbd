package com.example.spedy.dao;

import com.example.spedy.model.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository("postgres")
public class PostgresPersonDao implements PersonDao {


    @Override
    public boolean insertPerson(UUID id, Person person) {
        return false;
    }

    @Override
    public boolean insertPerson(Person person) {
        return false;
    }

    @Override
    public boolean deletePerson(UUID id, Person person) {
        return false;
    }

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public PostgresPersonDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Person> selectAllPeople() {
/*        final String sql = "SELECT id, name FROM person";
        List<Person> listPeople = jdbcTemplate.query(sql, (resultSet, i) -> {
            UUID id = UUID.fromString(resultSet.getString("id"));
            String name = resultSet.getString("name");
            return new Person(id, name);
        });
        return listPeople;*/
        return null;
    }


}

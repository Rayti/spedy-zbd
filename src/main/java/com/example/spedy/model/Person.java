package com.example.spedy.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.UUID;



public class Person {

    private final UUID id;

    private final String name;

    private  String description;

    public Person(@JsonProperty("id") UUID id,
                  @JsonProperty("name") String name) {
        this.id = id;
        this.name = name;
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescription() {
        return this.description;
    }
}

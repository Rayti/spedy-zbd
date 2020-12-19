package com.example.spedy.model;

import java.util.UUID;

public class Cargo {

    private final UUID id;

    private String name;

    private String description;

    public Cargo(UUID id, String name, String description) {
        this.id = id;
        assignArguments(name, description);
    }

    public Cargo(String name, String description) {
        this.id = UUID.randomUUID();
        assignArguments(name, description);
    }

    private void assignArguments(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Cargo cargo = (Cargo) o;

        return id.equals(cargo.id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }

    @Override
    public String toString() {
        return "Cargo{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}

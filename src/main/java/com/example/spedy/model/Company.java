package com.example.spedy.model;

import java.util.UUID;

public class Company {

    private final UUID id;

    private String name;
    private String description;
    private String country;
    private String city;
    private String address;

    public Company(UUID id, String name, String description, String country, String city, String address) {
        this.id = id;
        assignParameters(name, description, country, city, address);
    }

    public Company(String name, String description, String country, String city, String address) {
        this.id = UUID.randomUUID();
        assignParameters(name, description, country, city, address);
    }

    private void assignParameters(String name, String type, String country, String city, String address) {
        this.name = name;
        this.description = type;
        this.country = country;
        this.city = city;
        this.address = address;
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

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Company company = (Company) o;

        return id.equals(company.id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }

    @Override
    public String toString() {
        return "Company{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", country='" + country + '\'' +
                ", city='" + city + '\'' +
                ", address='" + address + '\'' +
                '}';
    }
}

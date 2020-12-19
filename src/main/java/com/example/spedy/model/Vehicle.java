package com.example.spedy.model;

import java.util.UUID;

public class Vehicle {

    private final UUID id;

    private String name;

    private int power;

    private int productionYear;

    public Vehicle(UUID id, String name, int power, int productionYear) {
        this.id = id;
        assignParameters(name, power, productionYear);
    }

    public Vehicle(String name, int power, int productionYear) {
        this.id = UUID.randomUUID();
        assignParameters(name, power, productionYear);
    }

    private void assignParameters(String name, int power, int productionYear) {
        this.name = name;
        this.power = power;
        this.productionYear = productionYear;
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

    public int getPower() {
        return power;
    }

    public void setPower(int power) {
        this.power = power;
    }

    public int getProductionYear() {
        return productionYear;
    }

    public void setProductionYear(int productionYear) {
        this.productionYear = productionYear;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Vehicle vehicle = (Vehicle) o;

        return id.equals(vehicle.id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }

    @Override
    public String toString() {
        return "Vehicle{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", power=" + power +
                ", productionYear=" + productionYear +
                '}';
    }
}

package com.example.spedy.model;

import java.sql.Date;
import java.util.UUID;

public class Refueling {

    private final UUID id;

    private final UUID vehicleId;

    private int amount;

    private double pricePerLitre;

    private String country;

    private String city;

    private Date refuelDate;

    public Refueling(UUID id, UUID vehicleId, int amount, double pricePerLitre, String country, String city, Date refuelDate) {
        this.id = id;
        this.vehicleId = vehicleId;
        this.amount = amount;
        this.pricePerLitre = pricePerLitre;
        this.country = country;
        this.city = city;
        this.refuelDate = refuelDate;
    }

    public Refueling(UUID vehicleId, int amount, double pricePerLitre, String country, String city, Date refuelDate) {
        this(UUID.randomUUID(), vehicleId, amount, pricePerLitre, country, city, refuelDate);
    }

    public UUID getId() {
        return id;
    }

    public UUID getVehicleId() {
        return vehicleId;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public double getPricePerLitre() {
        return pricePerLitre;
    }

    public void setPricePerLitre(double pricePerLitre) {
        this.pricePerLitre = pricePerLitre;
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

    public Date getRefuelDate() {
        return refuelDate;
    }

    public void setRefuelDate(Date refuelDate) {
        this.refuelDate = refuelDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Refueling refueling = (Refueling) o;

        return id.equals(refueling.id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }

    @Override
    public String toString() {
        return "Refueling{" +
                "id=" + id +
                ", vehicleId=" + vehicleId +
                ", amount=" + amount +
                ", pricePerLitre=" + pricePerLitre +
                ", country='" + country + '\'' +
                ", city='" + city + '\'' +
                ", refuelDate=" + refuelDate +
                '}';
    }
}

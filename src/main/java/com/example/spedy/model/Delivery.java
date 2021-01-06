package com.example.spedy.model;

import java.sql.Date;
import java.util.UUID;

public class Delivery {

    private final UUID deliveryId;
    private UUID employeeId;
    private UUID fromCompanyId;
    private UUID toCompanyId;
    private UUID vehicleId;
    private int weight;
    private UUID cargoId;
    private Date startDate;
    private Date finishDate;
    private String isFinished;

    public Delivery(UUID deliveryId, UUID employeeId, UUID fromCompanyId, UUID toCompanyId,
                    UUID vehicleId, int weight, UUID cargoId, Date startDate, Date finishDate, String isFinished) {
        this.deliveryId = deliveryId;
        this.employeeId = employeeId;
        this.fromCompanyId = fromCompanyId;
        this.toCompanyId = toCompanyId;
        this.vehicleId = vehicleId;
        this.weight = weight;
        this.cargoId = cargoId;
        this.startDate = startDate;
        this.finishDate = finishDate;
        this.isFinished = isFinished;
    }

    public Delivery(UUID employeeId, UUID fromCompanyId, UUID toCompanyId, UUID vehicleId,
                    int weight, UUID cargoId, Date startDate, Date finishDate, String isFinished) {
        this(UUID.randomUUID(), employeeId, fromCompanyId, toCompanyId, vehicleId,
                weight, cargoId, startDate, finishDate, isFinished);
    }

    public UUID getDeliveryId() {
        return deliveryId;
    }

    public UUID getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(UUID employeeId) {
        this.employeeId = employeeId;
    }

    public UUID getFromCompanyId() {
        return fromCompanyId;
    }

    public void setFromCompanyId(UUID fromCompanyId) {
        this.fromCompanyId = fromCompanyId;
    }

    public UUID getToCompanyId() {
        return toCompanyId;
    }

    public void setToCompanyId(UUID toCompanyId) {
        this.toCompanyId = toCompanyId;
    }

    public UUID getVehicleId() {
        return vehicleId;
    }

    public void setVehicleId(UUID vehicleId) {
        this.vehicleId = vehicleId;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public UUID getCargoId() {
        return cargoId;
    }

    public void setCargoId(UUID cargoId) {
        this.cargoId = cargoId;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getFinishDate() {
        return finishDate;
    }

    public void setFinishDate(Date finishDate) {
        this.finishDate = finishDate;
    }

    public String getIsFinished() {
        return isFinished;
    }

    public void setIsFinished(String isFinished) {
        this.isFinished = isFinished;
    }
}

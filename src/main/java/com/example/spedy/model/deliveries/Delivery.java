package com.example.spedy.model.deliveries;

import java.sql.Date;
import java.util.UUID;

public class Delivery {

    //obligatory
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

    //optional
    private String employeeFirstName;
    private String employeeLastName;
    private String fromCompanyName;
    private String toCompanyName;
    private String vehicleName;
    private String cargoName;

    public Delivery(DeliveryBuilder builder) {
        if(builder.deliveryId == null) throw new NullPointerException("DeliveryId missing");
        if(builder.employeeId == null) throw new NullPointerException("EmployeeId missing");
        if(builder.fromCompanyId == null || builder.toCompanyId == null) throw new NullPointerException("CompanyIds missing");
        if(builder.vehicleId == null) throw new NullPointerException("VehicleId missing");
        if(builder.cargoId == null) throw new NullPointerException("CargoId missing");
        if(builder.startDate == null) throw new NullPointerException("Dates missing");

        this.deliveryId = builder.deliveryId;
        this.employeeId = builder.employeeId;
        this.fromCompanyId = builder.fromCompanyId;
        this.toCompanyId = builder.toCompanyId;
        this.vehicleId = builder.vehicleId;
        this.weight = builder.weight;
        this.cargoId = builder.cargoId;
        this.startDate = builder.startDate;
        this.finishDate = builder.finishDate;
        this.isFinished = builder.isFinished;
        //
        this.employeeFirstName = builder.employeeFirstName;
        this.employeeLastName = builder.employeeLastName;
        this.fromCompanyName = builder.fromCompanyName;
        this.toCompanyName = builder.toCompanyName;
        this.vehicleName = builder.vehicleName;
        this.cargoName = builder.cargoName;
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

    public String getEmployeeFirstName() {
        return employeeFirstName;
    }

    public void setEmployeeFirstName(String employeeFirstName) {
        this.employeeFirstName = employeeFirstName;
    }

    public String getEmployeeLastName() {
        return employeeLastName;
    }

    public void setEmployeeLastName(String employeeLastName) {
        this.employeeLastName = employeeLastName;
    }

    public String getFromCompanyName() {
        return fromCompanyName;
    }

    public void setFromCompanyName(String fromCompanyName) {
        this.fromCompanyName = fromCompanyName;
    }

    public String getToCompanyName() {
        return toCompanyName;
    }

    public void setToCompanyName(String toCompanyName) {
        this.toCompanyName = toCompanyName;
    }

    public String getVehicleName() {
        return vehicleName;
    }

    public void setVehicleName(String vehicleName) {
        this.vehicleName = vehicleName;
    }

    public String getCargoName() {
        return cargoName;
    }

    public void setCargoName(String cargoName) {
        this.cargoName = cargoName;
    }
}

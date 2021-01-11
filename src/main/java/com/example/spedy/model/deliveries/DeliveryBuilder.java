package com.example.spedy.model.deliveries;

import java.sql.Date;
import java.util.UUID;

public class DeliveryBuilder {

    //obligatory
     UUID deliveryId;
     UUID employeeId;
     UUID fromCompanyId;
     UUID toCompanyId;
     UUID vehicleId;
     int weight;
     UUID cargoId;
     Date startDate;
     Date finishDate;
     String isFinished;

    //optional
     String employeeFirstName;
     String employeeLastName;
     String fromCompanyName;
     String toCompanyName;
     String vehicleName;
     String cargoName;

    private DeliveryBuilder() {
    }

    public static DeliveryBuilder begin() {
        return new DeliveryBuilder();
    }

    public Delivery get() {
        return new Delivery(this);
    }

    public DeliveryBuilder withObligatoryIds(UUID deliveryId,
                                             UUID employeeId,
                                             UUID fromCompanyId,
                                             UUID toCompanyId,
                                             UUID vehicleId,
                                             UUID cargoId){
        this.deliveryId = deliveryId;
        this.employeeId = employeeId;
        this.fromCompanyId = fromCompanyId;
        this.toCompanyId = toCompanyId;
        this.vehicleId = vehicleId;
        this.cargoId = cargoId;
        return this;
    }

    public DeliveryBuilder withObligatoryWeight(int weight){
        this.weight = weight;
        return this;
    }

    public DeliveryBuilder withObligatoryDates(Date startDate,
                                               Date finishDate,
                                               String isFinished) {
        this.startDate = startDate;
        this.finishDate = finishDate;
        this.isFinished = isFinished;
        return this;
    }

    public DeliveryBuilder withEmployeeData(String firstName, String lastName) {
        this.employeeFirstName = firstName;
        this.employeeLastName = lastName;
        return this;
    }

    public DeliveryBuilder withCompanyData(String fromCompanyName, String toCompanyName) {
        this.fromCompanyName = fromCompanyName;
        this.toCompanyName = toCompanyName;
        return this;
    }

    public DeliveryBuilder withVehicleData(String vehicleName) {
        this.vehicleName = vehicleName;
        return this;
    }

    public DeliveryBuilder withCargoData(String cargoName) {
        this.cargoName = cargoName;
        return this;
    }

}

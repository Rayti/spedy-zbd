package com.example.spedy.model;

public class DeliveryWithConnectedModels {

    private final Delivery delivery;
    private final Employee employee;
    private final Company fromCompany;
    private final Company toCompany;
    private final Vehicle vehicle;
    private final Cargo cargo;

    public DeliveryWithConnectedModels(Delivery delivery, Employee employee,
                                       Company fromCompany, Company toCompany,
                                       Vehicle vehicle, Cargo cargo) {
        this.delivery = delivery;
        this.employee = employee;
        this.fromCompany = fromCompany;
        this.toCompany = toCompany;
        this.vehicle = vehicle;
        this.cargo = cargo;
    }
}

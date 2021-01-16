package com.example.spedy.service;

import com.example.spedy.dao.deliveryDao.DeliveryDao;
import com.example.spedy.dao.deliveryDao.DeliveryOrderOptions;
import com.example.spedy.model.deliveries.Delivery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service("deliveryService")
public class DeliveryService {

    private final DeliveryDao deliveryDao;

    private String pattern;
    private DeliveryOrderOptions option;

    @Autowired
    public DeliveryService(@Qualifier("postgresDeliveryDao") DeliveryDao deliveryDao) {
        this.deliveryDao = deliveryDao;
        pattern = "";
        this.option = DeliveryOrderOptions.NONE;
    }

    public Delivery getDelivery(UUID deliveryId) {
        return deliveryDao.select(deliveryId);
    }

    public List<Delivery> getAllDeliveries() {
        List<Delivery> deliveries =  deliveryDao.selectAll(option);
        return isFilterable() ? filterList(deliveries) : deliveries;
    }

    public List<Delivery> getAllWithFromCompanyId(UUID fromCompanyId) {
        return deliveryDao.selectWithFromCompanyId(fromCompanyId, option);
    }

    public List<Delivery> getAllWithToCompanyID(UUID toCompanyId) {
        return  deliveryDao.selectWithToCompanyId(toCompanyId, option);
    }

    public List<Delivery> getAllUnfinished() {
        return deliveryDao.selectUnfinished(option);
    }

    public List<Delivery> getAllFinished() {
        return deliveryDao.selectFinished(option);
    }

    public List<Delivery> getAllStartedBeforeDate(Date date) {
        return deliveryDao.selectStartedBefore(date, option);
    }

    public List<Delivery> getAllStartedAfterDate(Date date){
        return deliveryDao.selectStartedAfter(date, option);
    }

    public List<Delivery> getAllStartedBetweenDates(Date minDate, Date maxDate){
        return deliveryDao.selectStartedBetweenDates(minDate, maxDate, option);
    }

    public String deleteDelivery(Delivery delivery) {
        String responseIfTrue = "Delivery deleted from database.";
        String responseIfFalse = "Could not delete delivery. Check spelling.";
        return deliveryDao.delete(delivery) ? responseIfTrue : responseIfFalse;
    }

    public String insertDelivery(Delivery delivery) {
        String responseIfTrue = "Delivery added to database.";
        String responseIfFalse = "Could not add delivery. Check spelling.";
        return deliveryDao.insert(delivery) ? responseIfTrue : responseIfFalse;
    }

    public String updateDelivery(Delivery delivery) {
        String responseIfTrue = "Delivery updated.";
        String responseIfFalse = "Could not update delivery. Check spelling.";
        return deliveryDao.update(delivery) ? responseIfTrue : responseIfFalse;
    }

    public DeliveryService withPattern(String pattern) {
        this.pattern = pattern;
        return this;
    }

    public DeliveryService withOrder(DeliveryOrderOptions option){
        this.option = option;
        return this;
    }

    private boolean isFilterable() {
        return !this.pattern.equals("");
    }

    private List<Delivery> filterList(List<Delivery> deliveries) {
        List<Delivery> filteredList = deliveries.stream()
                .filter(delivery -> {
                    String data = delivery.getEmployeeFirstName() +
                            delivery.getEmployeeLastName() +
                            delivery.getFromCompanyName() +
                            delivery.getToCompanyName() +
                            delivery.getVehicleName() +
                            delivery.getCargoName() +
                            delivery.getWeight();
                    return data.contains(pattern);
                })
                .collect(Collectors.toList());

        clearPattern();
        return filteredList;
    }

    private void clearPattern() {
        this.pattern = "";
    }

}

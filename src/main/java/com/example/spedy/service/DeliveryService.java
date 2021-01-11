package com.example.spedy.service;

import com.example.spedy.dao.deliveryDao.DeliveryDao;
import com.example.spedy.dao.deliveryDao.DeliveryOrderOptions;
import com.example.spedy.model.deliveries.Delivery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.List;
import java.util.UUID;

@Service("deliveryService")
public class DeliveryService {

    private final DeliveryDao deliveryDao;

    @Autowired
    public DeliveryService(@Qualifier("postgresDeliveryDao") DeliveryDao deliveryDao) {
        this.deliveryDao = deliveryDao;
    }

    public Delivery getDelivery(UUID deliveryId) {
        return deliveryDao.select(deliveryId);
    }

    public List<Delivery> getAllDeliveries(DeliveryOrderOptions option) {
        return deliveryDao.selectAll(option);
    }

    public List<Delivery> getAllDeliveries() {
        return getAllDeliveries(DeliveryOrderOptions.START_DATE_DESC);
    }

    public List<Delivery> getAllWithFromCompanyId(UUID fromCompanyId, DeliveryOrderOptions option) {
        return deliveryDao.selectWithFromCompanyId(fromCompanyId, option);
    }

    public List<Delivery> getAllWithToCompanyID(UUID toCompanyId, DeliveryOrderOptions option) {
        return  deliveryDao.selectWithToCompanyId(toCompanyId, option);
    }

    public List<Delivery> getAllUnfinished(DeliveryOrderOptions option) {
        return deliveryDao.selectUnfinished(option);
    }

    public List<Delivery> getAllFinished(DeliveryOrderOptions option) {
        return deliveryDao.selectFinished(option);
    }

    public List<Delivery> getAllStartedBeforeDate(Date date, DeliveryOrderOptions option) {
        return deliveryDao.selectStartedBefore(date, option);
    }

    public List<Delivery> getAllStartedAfterDate(Date date, DeliveryOrderOptions option){
        return deliveryDao.selectStartedAfter(date, option);
    }

    public List<Delivery> getAllStartedBetweenDates(Date minDate, Date maxDate, DeliveryOrderOptions option){
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

}

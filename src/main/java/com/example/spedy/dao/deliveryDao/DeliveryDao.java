package com.example.spedy.dao.deliveryDao;

import com.example.spedy.model.deliveries.Delivery;

import java.sql.Date;
import java.util.List;
import java.util.UUID;

public interface DeliveryDao{

    boolean insert(Delivery delivery);

    boolean update(Delivery delivery);

    boolean delete(Delivery delivery);

    List<Delivery> selectAll(DeliveryOrderOptions option);

    Delivery select(UUID deliveryId);

    List<Delivery> selectWithFromCompanyId(UUID companyId, DeliveryOrderOptions option);

    List<Delivery> selectWithToCompanyId(UUID companyId, DeliveryOrderOptions option);

    List<Delivery> selectUnfinished(DeliveryOrderOptions option);

    List<Delivery> selectFinished(DeliveryOrderOptions option);

    List<Delivery> selectStartedBefore(Date date, DeliveryOrderOptions option);

    List<Delivery> selectStartedAfter(Date date, DeliveryOrderOptions option);

    List<Delivery> selectStartedBetweenDates(Date minDate, Date maxDate, DeliveryOrderOptions option);

    List<Delivery> selectWithVehicleId(UUID vehicleId, DeliveryOrderOptions option);

}

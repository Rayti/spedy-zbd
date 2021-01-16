package com.example.spedy.dao.deliveryDao;

import com.example.spedy.model.Accident;

import java.util.List;
import java.util.UUID;

public interface AccidentDao {

    boolean insert(Accident accident);

    boolean update(Accident accident);

    boolean delete(Accident accident);

    Accident select(UUID accident_id);

    List<Accident> selectAll();

    List<Accident> selectAllForDeliveryId(UUID delivery_id);
}

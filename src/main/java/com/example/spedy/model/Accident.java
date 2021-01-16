package com.example.spedy.model;

import java.sql.Date;
import java.util.UUID;

public class Accident {

    private final UUID accidentId;
    private UUID deliveryId;
    private Date eventDate;
    private String description;

    public Accident(UUID accidentId, UUID deliveryId, Date eventDate, String description) {
        this.accidentId = accidentId;
        this.deliveryId = deliveryId;
        this.eventDate = eventDate;
        this.description = description;
    }

    public UUID getAccidentId() {
        return accidentId;
    }

    public UUID getDeliveryId() {
        return deliveryId;
    }

    public void setDeliveryId(UUID deliveryId) {
        this.deliveryId = deliveryId;
    }

    public Date getEventDate() {
        return eventDate;
    }

    public void setEventDate(Date eventDate) {
        this.eventDate = eventDate;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Accident accident = (Accident) o;

        return accidentId != null ? accidentId.equals(accident.accidentId) : accident.accidentId == null;
    }

    @Override
    public int hashCode() {
        return accidentId != null ? accidentId.hashCode() : 0;
    }

    public void setDescription(String description) {
        this.description = description;
    }


    @Override
    public String toString() {
        return "Accident{" +
                "accidentId=" + accidentId +
                ", deliveryId=" + deliveryId +
                ", event_date=" + eventDate +
                ", description='" + description + '\'' +
                '}';
    }
}

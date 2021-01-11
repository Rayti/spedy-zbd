package com.example.spedy.dao.deliveryDao;

public enum DeliveryOrderOptions {
    FROM_COMPANY_ASC(" (SELECT c0.name FROM companies c0 WHERE c0.company_id = from_company_id) "),
    FROM_COMPANY_DESC(" (SELECT c0.name FROM companies c0 WHERE c0.company_id = from_company_id) DESC "),
    TO_COMPANY_ASC(" (SELECT c0.name FROM companies c0 WHERE c0.company_id = to_company_id) "),
    TO_COMPANY_DESC(" (SELECT c0.name FROM companies c0 WHERE c0.company_id = to_company_id) DESC"),
    VEHICLE_ASC( " (SELECT v0.name FROM vehicles v0 WHERE v0.vehicle_id = vehicle_id) "),
    VEHICLE_DESC( " (SELECT v0.name FROM vehicles v0 WHERE v0.vehicle_id = vehicle_id) DESC "),
    WEIGHT_ASC( "weight "),
    WEIGHT_DESC(" weight DESC "),
    START_DATE_ASC(" start_date "),
    START_DATE_DESC(" stat_date DESC "),
    FINISH_DATE_ASC(" finish_date "),
    FINISH_DATE_DESC(" finish_date DESC "),
    IS_FINISHED_ASC(" is_finished "),
    IS_FINISHED_DESC(" is_finished DESC ");

    DeliveryOrderOptions(String sqlOrder) {
        this.sqlOrder = " ORDER BY " + sqlOrder + " NULLS FIRST ";
    }

    private final String sqlOrder;

    @Override
    public String toString() {
        return sqlOrder;
    }
}

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
    START_DATE_DESC(" start_date DESC "),
    FINISH_DATE_ASC(" finish_date "),
    FINISH_DATE_DESC(" finish_date DESC "),
    IS_FINISHED_ASC(" is_finished "),
    IS_FINISHED_DESC(" is_finished DESC "),
    NONE();

    DeliveryOrderOptions(String sqlOrder) {
        this.sqlOrder = " ORDER BY " + sqlOrder + " NULLS FIRST ";
    }

    DeliveryOrderOptions(){ this.sqlOrder = ""; }

    private final String sqlOrder;

    public static DeliveryOrderOptions getOptionByNumber(int number) {
        switch (number){
            case 1:
                return DeliveryOrderOptions.FROM_COMPANY_ASC;
            case 2:
                return DeliveryOrderOptions.FROM_COMPANY_DESC;
            case 3:
                return DeliveryOrderOptions.TO_COMPANY_ASC;
            case 4:
                return DeliveryOrderOptions.TO_COMPANY_DESC;
            case 5:
                return DeliveryOrderOptions.VEHICLE_ASC;
            case 6:
                return DeliveryOrderOptions.VEHICLE_DESC;
            case 7:
                return DeliveryOrderOptions.WEIGHT_ASC;
            case 8:
                return DeliveryOrderOptions.WEIGHT_DESC;
            case 9:
                return DeliveryOrderOptions.START_DATE_ASC;
            case 10:
                return DeliveryOrderOptions.START_DATE_DESC;
            case 11:
                return DeliveryOrderOptions.FINISH_DATE_ASC;
            case 12:
                return DeliveryOrderOptions.FINISH_DATE_DESC;
            case 13:
                return DeliveryOrderOptions.IS_FINISHED_ASC;
            case 14:
                return DeliveryOrderOptions.IS_FINISHED_DESC;
        }
        return DeliveryOrderOptions.NONE;
    }

    @Override
    public String toString() {
        return sqlOrder;
    }
}

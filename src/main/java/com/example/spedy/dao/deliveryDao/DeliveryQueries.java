package com.example.spedy.dao.deliveryDao;

public class DeliveryQueries {

    private final static String INSERT = "INSERT INTO " +
            "deliveries(delivery_id, employee_id, from_company_id, to_company_id, " +
            "vehicle_id, weight, cargo_id, start_date, finish_date, is_finished) " +
            "VALUES( ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
    private final static String DELETE = "DELETE FROM deliveries " +
            "WHERE delivery_id = ?";
    private final static String UPDATE = "UPDATE deliveries " +
            "SET employee_id = ?, " +
            "from_company_id = ?, " +
            "to_company_id = ?, " +
            "vehicle_id = ?," +
            "weight = ?, " +
            "cargo_id = ?," +
            "start_date = ?, " +
            "finish_date = ?, " +
            "is_finished = ? " +
            "WHERE delivery_id = ?";
    private final static String SELECT_ALL = "SELECT deliveries.*, " +
            "employees.first_name AS employee_first_name, " +
            "employees.last_name AS employee_last_name, " +
            "fromC.name AS from_company_name, " +
            "toC.name AS to_company_name," +
            "vehicles.name AS vehicle_name, " +
            "cargo.name AS cargo_name " +
                "FROM deliveries, employees, " +
            "companies fromC, companies toC, vehicles, cargo " +
                "WHERE deliveries.employee_id = employees.employee_id " +
            "AND deliveries.from_company_id = fromC.company_id " +
            "AND deliveries.to_company_id = toC.company_id " +
            "AND deliveries.vehicle_id = vehicles.vehicle_id " +
            "AND deliveries.cargo_id = cargo.cargo_id ";

    public static String getSelectQuery() {
        return SELECT_ALL + " AND deliveries.delivery_id = ?";
    }

    public static String getInsertQuery() {
        return INSERT;
    }

    public static String getDeleteQuery(){
        return DELETE;
    }

    public static String getUpdateQuery() {
        return UPDATE;
    }

    public static String getSelectAllQuery(DeliveryOrderOptions option){
        return SELECT_ALL + option;
    }

    public static String getSelectAllWithFromCompanyIdQuery(DeliveryOrderOptions option) {
        return SELECT_ALL + " AND deliveries.from_company_id = ? " + option;
    }

    public static String getSelectAllWithToCompanyIdQuery(DeliveryOrderOptions option){
        return SELECT_ALL + " AND deliveries.to_company_id = ? " + option;
    }

    public static String getSelectAllUnfinishedQuery(DeliveryOrderOptions option) {
        return SELECT_ALL + " AND deliveries.is_finished = 'NO' " + option;
    }

    public static String getSelectAllFinishedQuery(DeliveryOrderOptions option){
        return SELECT_ALL + " AND deliveries.is_finished = 'YES' " + option;
    }

    public static String getSelectAllStartedBeforeQuery(DeliveryOrderOptions option) {
        return SELECT_ALL + " AND deliveries.start_date < ? " + option;
    }

    public static String getSelectAllStartedAfterQuery(DeliveryOrderOptions option){
        return SELECT_ALL + " AND deliveries.start_date > ? " + option;
    }

    public static String getSelectAllStartedBetweenDatesQuery(DeliveryOrderOptions option){
        return SELECT_ALL + " AND deliveries.start_date > ? AND deliveries.start_date < ? " + option;
    }

    public static String getSelectAllWithVehicleIdQuery(DeliveryOrderOptions option){
        return SELECT_ALL + " AND deliveries.vehicle_id = ? " + option;
    }
}

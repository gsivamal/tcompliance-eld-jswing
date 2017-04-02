package domain.model;

import dao.*;
import domain.model.factory.*;

import java.sql.SQLException;
import java.time.LocalDateTime;

public class Mediator {

    private DriverList driverList;
    private VehicleList vehicleList;
    private Driver loggedDriver;
    private DriverDatabaseDAO driverDatabase = DriverDatabaseDAO.getInstance();
    private LogbookDatabaseDAO logbookDatabase = LogbookDatabaseDAO.getInstance();
    private FuelCardDatabaseDAO fuelCardDatabase = FuelCardDatabaseDAO.getInstance();
    private LoadDatabaseDAO loadDatabase = LoadDatabaseDAO.getInstance();
    private VehicleDatabaseDAO vehicleDatabase = VehicleDatabaseDAO.getInstance();


    private static final Mediator instance = new Mediator();
    public static Mediator getInstance(){
        return instance;
    }

    private Mediator(){
        try {
            driverList = driverDatabase.getAll();
            vehicleList = vehicleDatabase.getAll();
            DriverFactory.getInstance().setCount( driverDatabase.getLastID() );
            FuelCardFactory.getInstance().setCount( fuelCardDatabase.getLastID() );
            LoadFactory.getInstance().setCount( loadDatabase.getLastID() );
            LogbookFactory.getInstance().setCount( logbookDatabase.getLastID() );
            VehicleFactory.getInstance().setCount( vehicleDatabase.getLastID() );
            System.out.println(vehicleDatabase.getLastID());
            LogbookStatus.insertValuesDatabase();
            DutyStatus.insertValuesDatabase();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }


    public void addDriver(Driver driver) {
        driverList.add( driver );
        driverDatabase.add( driver );
    }
    public void updateDriver(Driver driver) {
        driverDatabase.update( driver );
    }
    public void removeDriver(Driver driver) {
        driverList.remove( driver );
        driverDatabase.remove( driver );
    }
    public Driver login(String username, String password) {
        loggedDriver = driverList.getDriver( username, password );
        return loggedDriver;
    }
    public Driver getLoggedDriver(){
        return loggedDriver;
    }

    public void changeStatus(LogbookStatus approveStatus, String additionalInfo, String notes, DutyStatus newStatus) {
        if ( getLoggedDriver().getLogbookList().last().getDutyStatus().equals( newStatus ) ) {
            return;
        }
        LocalDateTime endTime = LocalDateTime.now();
        LocalDateTime approveTime = LocalDateTime.now();
        Logbook newLogbook = LogbookFactory.getInstance().getLogbook( newStatus );
        changeStatus( endTime, approveStatus, approveTime, 100, additionalInfo, notes, newLogbook);
    }

    private void changeStatus(LocalDateTime endTime, LogbookStatus logbookStatus, LocalDateTime approveTime, int mileage, String additionalInfo, String notes, Logbook newStatus) {
        LogbookList logbookList = getLoggedDriver().getLogbookList();
        Logbook update = logbookList.last();
        logbookList.changeStatus( endTime, logbookStatus, approveTime, mileage, additionalInfo, notes, newStatus );
        Logbook add = logbookList.last();
        logbookDatabase.update( update );
        logbookDatabase.add( add );
    }


    public void addFuelCard(FuelCard fuelCard) {
        fuelCardDatabase.add( fuelCard );
    }
    public void updateFuelCard(FuelCard fuelCard) {
        fuelCardDatabase.update( fuelCard );
    }
    public void removeFuelCard(FuelCard fuelCard) {
        fuelCardDatabase.remove( fuelCard );
    }

    public void addVehicle(Vehicle vehicle) {
        vehicleList.add( vehicle );
        vehicleDatabase.add( vehicle );
    }
    public void updateVehicle(Vehicle vehicle) {
        vehicleDatabase.update( vehicle );
    }
    public void removeVehicle(Vehicle vehicle) {
        vehicleList.remove( vehicle );
        vehicleDatabase.remove( vehicle );
    }

    public void addLoad(Load load) throws SQLException{
        loadDatabase.add( load );
    }
    public void updateLoad(Load load) throws SQLException{
        loadDatabase.update( load );
    }
    public void removeLoad(Load load) throws SQLException{
        loadDatabase.remove( load );
    }

    public DriverList getDriverList() {
        return driverList;
    }

    public VehicleList getVehicleList() {
        return vehicleList;
    }

}

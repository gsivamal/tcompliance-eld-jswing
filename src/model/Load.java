package model;

import javafx.beans.property.*;

import java.time.LocalDateTime;

public class Load {
    private int loadID;
    private ObjectProperty<LocalDateTime> startTime = new SimpleObjectProperty<>();
    private ObjectProperty<LocalDateTime> endTime = new SimpleObjectProperty<>();
    private ObjectProperty<Vehicle> truck = new SimpleObjectProperty<>();
    private ObjectProperty<Vehicle> trailer1 = new SimpleObjectProperty<>();
    private ObjectProperty<Vehicle> trailer2 = new SimpleObjectProperty<>();
    private StringProperty blNumber = new SimpleStringProperty();
    private ObjectProperty<FuelCard> fuelCard = new SimpleObjectProperty<>();
    private ObjectProperty<Driver> driver = new SimpleObjectProperty<>();


    public Load(int loadID, LocalDateTime startTime, LocalDateTime endTime, Vehicle truck, Vehicle trailer1, Vehicle trailer2, String blNumber, FuelCard fuelCard, Driver driver) {
        setLoadID( loadID );
        setStartTime( startTime );
        setEndTime( endTime );
        setTruck( truck );
        setTrailer1( trailer1 );
        setTrailer2( trailer2 );
        setBlNumber( blNumber );
        setFuelCard( fuelCard );
        setDriver( driver );
    }

    public int getLoadID() {
        return loadID;
    }

    public void setLoadID(int loadID) {
        this.loadID = loadID;
    }

    public LocalDateTime getStartTime() {
        return startTime.get();
    }

    public ObjectProperty<LocalDateTime> startTimeProperty() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime.set( startTime );
    }

    public LocalDateTime getEndTime() {
        return endTime.get();
    }

    public ObjectProperty<LocalDateTime> endTimeProperty() {
        return endTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime.set( endTime );
    }

    public Vehicle getTruck() {
        return truck.get();
    }

    public ObjectProperty<Vehicle> truckProperty() {
        return truck;
    }

    public void setTruck(Vehicle truck) {
        this.truck.set( truck );
    }

    public Vehicle getTrailer1() {
        return trailer1.get();
    }

    public ObjectProperty<Vehicle> trailer1Property() {
        return trailer1;
    }

    public void setTrailer1(Vehicle trailer1) {
        this.trailer1.set( trailer1 );
    }

    public Vehicle getTrailer2() {
        return trailer2.get();
    }

    public ObjectProperty<Vehicle> trailer2Property() {
        return trailer2;
    }

    public void setTrailer2(Vehicle trailer2) {
        this.trailer2.set( trailer2 );
    }

    public String getBlNumber() {
        return blNumber.get();
    }

    public StringProperty blNumberProperty() {
        return blNumber;
    }

    public void setBlNumber(String blNumber) {
        this.blNumber.set( blNumber );
    }

    public FuelCard getFuelCard() {
        return fuelCard.get();
    }

    public ObjectProperty<FuelCard> fuelCardProperty() {
        return fuelCard;
    }

    public void setFuelCard(FuelCard fuelCard) {
        this.fuelCard.set( fuelCard );
    }

    public Driver getDriver() {
        return driver.get();
    }

    public ObjectProperty<Driver> driverProperty() {
        return driver;
    }

    public void setDriver(Driver driver) {
        this.driver.set( driver );
    }
}

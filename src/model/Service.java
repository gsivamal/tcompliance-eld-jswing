package model;

import javafx.beans.property.*;
import javafx.collections.ListChangeListener;

import java.time.LocalDateTime;

public class Service {

    private IntegerProperty serviceID = new SimpleIntegerProperty();
    private ObjectProperty<User> driver = new SimpleObjectProperty<>();
    private ObjectProperty<User> coDriver = new SimpleObjectProperty<>();
    private StringProperty notificationMessage = new SimpleStringProperty();
    private ObjectProperty<Fuel> fuel = new SimpleObjectProperty<>();
    private ObjectProperty<Load> load = new SimpleObjectProperty<>();
    private ObjectProperty<Status> currentStatus = new SimpleObjectProperty<>();
    private ObjectProperty<StatusHistory> statusHistory = new SimpleObjectProperty<>();
    private StringProperty startDate = new SimpleStringProperty( "" );
    private IntegerProperty vehicleID = new SimpleIntegerProperty();
    private RulesList rules;

    public Service(Integer id, User driver, User coDriver, Fuel fuel, Load load, StatusHistory statusHistory, Status status) {
        setServiceID( id );
        setDriver( driver );
        setCoDriver( coDriver );
        setFuel( fuel );
        setLoad( load );
        this.statusHistory.set( statusHistory );
        this.statusHistory.get().observableList().addListener( (ListChangeListener<Status>) c ->  {
            if(this.statusHistory.get().size() > 0){
                startDate.set( this.statusHistory.get().get( 0 ).getStartTime().toLocalDate().toString() );
            }
        });
        setCurrentStatus( status );
    }

    public Status getCurrentStatus() {
        return currentStatus.get();
    }

    public ObjectProperty<Status> currentStatusProperty() {
        return currentStatus;
    }

    public void setCurrentStatus(Status currentStatus) {
        if ( getCurrentStatus() != null ) {
            if(getCurrentStatus().getStatusValue().equals( currentStatus.getStatusValue() ))
                return;
            getCurrentStatus().setEndTime( LocalDateTime.now() );
            Mediator.getInstance().updateStatus( this, getCurrentStatus() );
            statusHistory.get().addStatus( getCurrentStatus() );
        }
        this.currentStatus.set( currentStatus );
    }

    public String getNotificationMessage() {
        return notificationMessage.get();
    }

    public StringProperty notificationMessageProperty() {
        return notificationMessage;
    }

    public void setNotificationMessage(String notificationMessage) {
        this.notificationMessage.set( notificationMessage );
    }

    public StatusHistory getStatusHistory() {
        return statusHistory.get();
    }

    public ObjectProperty<StatusHistory> statusHistoryProperty() {
        return statusHistory;
    }

    public Status getFirstStatus(){
        return statusHistory.get().get( 0 );
    }

    public StringProperty startDateProperty() {
        return startDate;
    }

    public Fuel getFuel() {
        return fuel.get();
    }

    public ObjectProperty<Fuel> fuelProperty() {
        return fuel;
    }

    public void setFuel(Fuel fuel) {
        this.fuel.set( fuel );
    }

    public Load getLoad() {
        return load.get();
    }

    public ObjectProperty<Load> loadProperty() {
        return load;
    }

    public void setLoad(Load load) {
        this.load.set( load );
    }

    public int getServiceID() {
        return serviceID.get();
    }

    public IntegerProperty serviceIDProperty() {
        return serviceID;
    }

    public void setServiceID(int serviceID) {
        this.serviceID.set( serviceID );
    }

    public int getVehicleID() {
        return vehicleID.get();
    }

    public IntegerProperty vehicleIDProperty() {
        return vehicleID;
    }

    public void setVehicleID(int vehicleID) {
        this.vehicleID.set( vehicleID );
    }

    public User getDriver() {
        return driver.get();
    }

    public ObjectProperty<User> driverProperty() {
        return driver;
    }

    public void setDriver(User driver) {
        this.driver.set( driver );
    }

    public User getCoDriver() {
        return coDriver.get();
    }

    public ObjectProperty<User> coDriverProperty() {
        return coDriver;
    }

    public void setCoDriver(User coDriver) {
        this.coDriver.set( coDriver );
    }

    public RulesList getRules() {
        return rules;
    }

    public void setRules(RulesList rules) {
        this.rules = rules;
    }
}

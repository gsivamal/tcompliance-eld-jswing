package model;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.ListChangeListener;

import java.time.LocalDateTime;

public class Service {

    private ObjectProperty<Status> currentStatus = new SimpleObjectProperty<>();
    private StringProperty driverName = new SimpleStringProperty();
    private StringProperty coDriverName = new SimpleStringProperty();
    private SimpleStringProperty notificationMessage = new SimpleStringProperty();
    private SimpleObjectProperty<StatusHistory> statusHistory = new SimpleObjectProperty<>();
    private SimpleStringProperty startDate = new SimpleStringProperty( "" );

    public Service(String driverName, String coDriverName){
        setDriverName( driverName );
        setCoDriverName( coDriverName );
        statusHistory.set( new StatusHistory() );
        statusHistory.get().observableList().addListener( (ListChangeListener<Status>) c ->  {
            if(statusHistory.get().size() > 0){
                startDate.set( statusHistory.get().get( 0 ).getStartTime().toLocalDate().toString() );
            }
        });
        setCurrentStatus( new Status( Status.StatusValue.OFF ) );

    }


    public Status getCurrentStatus() {
        return currentStatus.get();
    }

    public ObjectProperty<Status> currentStatusProperty() {
        return currentStatus;
    }

    public void setCurrentStatus(Status currentStatus) {
        if ( getCurrentStatus() != null ) {
            getCurrentStatus().setEndTime( LocalDateTime.now() );
            statusHistory.get().addStatus( getCurrentStatus() );
        }
        this.currentStatus.set( currentStatus );
    }

    public String getDriverName() {
        return driverName.get();
    }

    public StringProperty driverNameProperty() {
        return driverName;
    }

    public void setDriverName(String driverName) {
        this.driverName.set( driverName );
    }

    public String getCoDriverName() {
        return coDriverName.get();
    }

    public StringProperty coDriverNameProperty() {
        return coDriverName;
    }

    public void setCoDriverName(String coDriverName) {
        this.coDriverName.set( coDriverName );
    }

    public String getNotificationMessage() {
        return notificationMessage.get();
    }

    public SimpleStringProperty notificationMessageProperty() {
        return notificationMessage;
    }

    public void setNotificationMessage(String notificationMessage) {
        this.notificationMessage.set( notificationMessage );
    }

    public Status getFirstStatus(){
        return statusHistory.get().get( 0 );
    }

    public SimpleStringProperty startDateProperty() {
        return startDate;
    }

}

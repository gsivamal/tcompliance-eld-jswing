package domain.model;

import javafx.beans.property.*;

import java.time.Duration;
import java.time.LocalDateTime;

public class Logbook {
    private int ID;
    private ObjectProperty<DutyStatus> dutyStatus = new SimpleObjectProperty<>();
    private ObjectProperty<LocalDateTime> startTime = new SimpleObjectProperty<>();
    private ObjectProperty<LocalDateTime> endTime = new SimpleObjectProperty<>();
    private ObjectProperty<Duration> duration = new SimpleObjectProperty<>();
    private ObjectProperty<Driver> driver = new SimpleObjectProperty<>();
    private ObjectProperty<GPSLocation> gpsLocation = new SimpleObjectProperty<>();
    private ObjectProperty<LogbookStatus> approvedStatus = new SimpleObjectProperty<>();
    private ObjectProperty<LocalDateTime> approvedTime = new SimpleObjectProperty<>();
    private IntegerProperty mileage = new SimpleIntegerProperty();
    private SimpleStringProperty additionalInfo = new SimpleStringProperty();
    private SimpleStringProperty notes = new SimpleStringProperty();

    public Logbook(int ID, DutyStatus dutyStatus, LocalDateTime startTime, LocalDateTime endTime, Driver driver,
                   GPSLocation gpsLocation, LogbookStatus approvedStatus, LocalDateTime approvedTime, int mileage, String additionalInfo, String notes) {
        setID( ID );

        this.startTime.addListener( (observable, oldValue, newValue) -> {
            if(getEndTime() != null)
                duration.set( Duration.between( getStartTime(), getEndTime() ) );
        } );
        this.endTime.addListener( (observable, oldValue, newValue) -> {
            if(startTime != null)
                duration.set( Duration.between( getStartTime(), getEndTime() ) );
        } );

        setDutyStatus( dutyStatus );
        setStartTime( startTime );
        setEndTime( endTime );
        setDriver( driver );
        setGpsLocation( gpsLocation );
        setApprovedStatus( approvedStatus );
        setApprovedTime( approvedTime );
        setMileage( mileage );
        setAdditionalInfo( additionalInfo );
        setNotes( notes );
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public DutyStatus getDutyStatus() {
        return dutyStatus.get();
    }

    public ObjectProperty<DutyStatus> dutyStatusProperty() {
        return dutyStatus;
    }

    public void setDutyStatus(DutyStatus dutyStatus) {
        this.dutyStatus.set( dutyStatus );
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

    public Driver getDriver() {
        return driver.get();
    }

    public ObjectProperty<Driver> driverProperty() {
        return driver;
    }

    public void setDriver(Driver driver) {
        this.driver.set( driver );
    }

    public GPSLocation getGpsLocation() {
        return gpsLocation.get();
    }

    public ObjectProperty<GPSLocation> gpsLocationProperty() {
        return gpsLocation;
    }

    public void setGpsLocation(GPSLocation gpsLocation) {
        this.gpsLocation.set( gpsLocation );
    }

    public LogbookStatus getApprovedStatus() {
        return approvedStatus.get();
    }

    public ObjectProperty<LogbookStatus> approvedStatusProperty() {
        return approvedStatus;
    }

    public void setApprovedStatus(LogbookStatus approvedStatus) {
        this.approvedStatus.set( approvedStatus );
    }

    public LocalDateTime getApprovedTime() {
        return approvedTime.get();
    }

    public ObjectProperty<LocalDateTime> approvedTimeProperty() {
        return approvedTime;
    }

    public void setApprovedTime(LocalDateTime approvedTime) {
        this.approvedTime.set( approvedTime );
    }

    public int getMileage() {
        return mileage.get();
    }

    public IntegerProperty mileageProperty() {
        return mileage;
    }

    public void setMileage(int mileage) {
        this.mileage.set( mileage );
    }

    public String getAdditionalInfo() {
        return additionalInfo.get();
    }

    public SimpleStringProperty additionalInfoProperty() {
        return additionalInfo;
    }

    public void setAdditionalInfo(String additionalInfo) {
        this.additionalInfo.set( additionalInfo );
    }

    public String getNotes() {
        return notes.get();
    }

    public SimpleStringProperty notesProperty() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes.set( notes );
    }

    public Duration getDuration() {
        return duration.get();
    }

    public ObjectProperty<Duration> durationProperty() {
        return duration;
    }
}

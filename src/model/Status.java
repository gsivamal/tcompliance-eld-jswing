package model;

import javafx.beans.property.*;

import java.time.Duration;
import java.time.LocalDateTime;

public class Status {
    private IntegerProperty statusID = new SimpleIntegerProperty();
    private ObjectProperty<StatusValue> statusValue = new SimpleObjectProperty<>();
    private ObjectProperty<LocalDateTime> startTime = new SimpleObjectProperty<>();
    private ObjectProperty<LocalDateTime> endTime = new SimpleObjectProperty<>();
    private ObjectProperty<Duration> duration = new SimpleObjectProperty<>();
    private StringProperty location = new SimpleStringProperty( "Austin TX" );
    private StringProperty notes = new SimpleStringProperty( "Notes" );
    private static int count = 1;

    public Status(Integer statusID, StatusValue statusValue, LocalDateTime startTime, LocalDateTime endTime) {
        setStatusID( statusID );
        this.startTime.addListener( (observable, oldValue, newValue) -> {
            if(getEndTime() != null)
                duration.set( Duration.between( getStartTime(), getEndTime() ) );
        } );
        this.endTime.addListener( (observable, oldValue, newValue) -> {
            if(startTime != null)
                duration.set( Duration.between( getStartTime(), getEndTime() ) );
        } );
        setStatusValue( statusValue );
        setStartTime( startTime );
        setEndTime( startTime );
    }


    public StatusValue getStatusValue() {
        return statusValue.get();
    }

    public ObjectProperty<StatusValue> statusValueProperty() {
        return statusValue;
    }

    public void setStatusValue(StatusValue statusValue) {
        this.statusValue.set( statusValue );
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

    public Duration getDuration() {
        return duration.get();
    }

    public ObjectProperty<Duration> durationProperty() {
        return duration;
    }

    public String getLocation() {
        return location.get();
    }

    public StringProperty locationProperty() {
        return location;
    }

    public void setDuration(Duration duration) {
        this.duration.set( duration );
    }

    public void setLocation(String location) {
        this.location.set( location );
    }

    public String getNotes() {
        return notes.get();
    }

    public StringProperty notesProperty() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes.set( notes );
    }

    public int getStatusID() {
        return statusID.get();
    }

    public IntegerProperty statusIDProperty() {
        return statusID;
    }

    public void setStatusID(int statusID) {
        this.statusID.set( statusID );
    }

    public enum StatusValue {

        OFF( "#c00000" ),
        SB( "#4f81bd" ),
        DRV( "#4f6228" ),
        ON( "#e46c0a" );

        private String colorHexCode;

        StatusValue(String colorHexCode) {
            this.colorHexCode = colorHexCode;
        }

        public String getColorHexCode(){
            return this.colorHexCode;
        }
    }

}

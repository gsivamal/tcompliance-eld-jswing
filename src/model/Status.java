package model;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;

import java.time.Duration;
import java.time.LocalDateTime;

public class Status {
    private ObjectProperty<StatusValue> statusValue = new SimpleObjectProperty<>();
    private ObjectProperty<LocalDateTime> startTime = new SimpleObjectProperty<>();
    private ObjectProperty<LocalDateTime> endTime = new SimpleObjectProperty<>();
    private ObjectProperty<Duration> duration = new SimpleObjectProperty<>();
    private SimpleStringProperty location = new SimpleStringProperty( "Austin TX" );
    private SimpleStringProperty notes = new SimpleStringProperty( "Notes" );

    public Status(StatusValue statusValue, LocalDateTime startTime) {
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

    public Status(StatusValue statusValue) {
        this( statusValue, LocalDateTime.now() );
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

    public SimpleStringProperty locationProperty() {
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

    public SimpleStringProperty notesProperty() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes.set( notes );
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

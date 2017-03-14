package model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;

import java.time.LocalDateTime;

public class Load {
    private IntegerProperty loadNumber = new SimpleIntegerProperty();
    private ObjectProperty<LocalDateTime> startTime = new SimpleObjectProperty<>();
    private ObjectProperty<LocalDateTime> endTime = new SimpleObjectProperty<>();

    public Load(int loadNumber, LocalDateTime startTime, LocalDateTime endTime) {
        setLoadNumber( loadNumber );
        setStartTime( startTime );
        setEndTime( endTime );
    }

    public int getLoadNumber() {
        return loadNumber.get();
    }

    public IntegerProperty loadNumberProperty() {
        return loadNumber;
    }

    public void setLoadNumber(int loadNumber) {
        this.loadNumber.set( loadNumber );
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
}

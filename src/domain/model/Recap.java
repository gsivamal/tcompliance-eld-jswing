package domain.model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;

import java.time.Duration;
import java.time.LocalDate;

public class Recap {

    private ObjectProperty<LocalDate> date = new SimpleObjectProperty<>();
    private ObjectProperty<Duration> drivingDuration = new SimpleObjectProperty<>();
    private ObjectProperty<Duration> onDutyDuration = new SimpleObjectProperty<>();
    private IntegerProperty mileage = new SimpleIntegerProperty();
    private ObjectProperty<Duration> totalDuration = new SimpleObjectProperty<>();

    public Recap(LocalDate date, Duration drivingDuration, Duration onDutyDuration, int mileage) {
        this.drivingDuration.addListener( observable -> updateTotal() );
        this.onDutyDuration.addListener( observable -> updateTotal() );
        setDate( date );
        setDrivingDuration( drivingDuration );
        setOnDutyDuration( onDutyDuration );
        setMileage( mileage );
    }

    public void addDrivingDuration(Duration duration) {
        setDrivingDuration( getDrivingDuration().plus( duration ) );
    }

    public void addOnDutyDuration(Duration duration) {
        setOnDutyDuration( getOnDutyDuration().plus( duration ) );
    }

    public void addMileage(int mileage) {
        setMileage( getMileage() + mileage );
    }

    public LocalDate getDate() {
        return date.get();
    }

    public ObjectProperty<LocalDate> dateProperty() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date.set( date );
    }

    public Duration getDrivingDuration() {
        return drivingDuration.get();
    }

    public ObjectProperty<Duration> drivingDurationProperty() {
        return drivingDuration;
    }

    public void setDrivingDuration(Duration drivingDuration) {
        this.drivingDuration.set( drivingDuration );
    }

    public Duration getOnDutyDuration() {
        return onDutyDuration.get();
    }

    public ObjectProperty<Duration> onDutyDurationProperty() {
        return onDutyDuration;
    }

    public void setOnDutyDuration(Duration onDutyDuration) {
        this.onDutyDuration.set( onDutyDuration );
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

    public Duration getTotalDuration() {
        return totalDuration.get();
    }

    public ObjectProperty<Duration> totalDurationProperty() {
        return totalDuration;
    }

    private void updateTotal(){
        Duration drivingDuration = getDrivingDuration() != null ? getDrivingDuration() : Duration.ZERO;
        Duration onDutyDuration = getOnDutyDuration() != null ? getOnDutyDuration() : Duration.ZERO;
        this.totalDuration.set( drivingDuration.plus( onDutyDuration ) );
    }
}

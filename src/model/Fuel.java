package model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Fuel {
    private StringProperty trailer1Status = new SimpleStringProperty();
    private StringProperty trailer2Status = new SimpleStringProperty();
    private IntegerProperty fuelCardNumber = new SimpleIntegerProperty();

    public Fuel(int fuelCardNumber, String trailer1Status, String trailer2Status) {
        setFuelCardNumber( fuelCardNumber );
        setTrailer1Status( trailer1Status );
        setTrailer2Status( trailer2Status );
    }

    public String getTrailer1Status() {
        return trailer1Status.get();
    }

    public StringProperty trailer1StatusProperty() {
        return trailer1Status;
    }

    public void setTrailer1Status(String trailer1Status) {
        this.trailer1Status.set( trailer1Status );
    }

    public String getTrailer2Status() {
        return trailer2Status.get();
    }

    public StringProperty trailer2StatusProperty() {
        return trailer2Status;
    }

    public void setTrailer2Status(String trailer2Status) {
        this.trailer2Status.set( trailer2Status );
    }

    public int getFuelCardNumber() {
        return fuelCardNumber.get();
    }

    public IntegerProperty fuelCardNumberProperty() {
        return fuelCardNumber;
    }

    public void setFuelCardNumber(int fuelCardNumber) {
        this.fuelCardNumber.set( fuelCardNumber );
    }
}

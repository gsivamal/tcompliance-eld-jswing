package domain.model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Vehicle {

    private int ID;
    private StringProperty name = new SimpleStringProperty();
    private StringProperty make = new SimpleStringProperty();
    private StringProperty vin = new SimpleStringProperty();

    public Vehicle(int ID, String name, String make, String vin) {
        setID( ID );
        setName( name );
        setMake( make );
        setVin( vin );
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getName() {
        return name.get();
    }

    public StringProperty nameProperty() {
        return name;
    }

    public void setName(String name) {
        this.name.set( name );
    }

    public String getMake() {
        return make.get();
    }

    public StringProperty makeProperty() {
        return make;
    }

    public void setMake(String make) {
        this.make.set( make );
    }

    public String getVin() {
        return vin.get();
    }

    public StringProperty vinProperty() {
        return vin;
    }

    public void setVin(String vin) {
        this.vin.set( vin );
    }

    @Override
    public String toString() {
        return String.format( "%s %s", getName(), getVin() );
    }


}

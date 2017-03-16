package model;

import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;

public class DriverList extends SimpleListProperty<Driver>{

    public DriverList(){
        super( FXCollections.observableArrayList() );
    }

    public Driver getDriver(String username, String password) {
        for (Driver driver : this) {
            if ( driver.getUsername().equalsIgnoreCase( username ) && driver.checkPassword( password ) ) {
                return driver;
            }
        }
        return null;
    }
}

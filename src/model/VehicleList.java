package model;

import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;

public class VehicleList extends SimpleListProperty<Vehicle>{

    public VehicleList(){
        super( FXCollections.observableArrayList());
    }
}

package domain.model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class FuelCard {

    private int id;
    private IntegerProperty number = new SimpleIntegerProperty();
    private StringProperty issuedBy = new SimpleStringProperty();

    public FuelCard(int id, int number, String issuedBy) {
        setId( id );
        setNumber( number );
        setIssuedBy( issuedBy );
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getNumber() {
        return number.get();
    }

    public IntegerProperty numberProperty() {
        return number;
    }

    public void setNumber(int number) {
        this.number.set( number );
    }

    public String getIssuedBy() {
        return issuedBy.get();
    }

    public StringProperty issuedByProperty() {
        return issuedBy;
    }

    public void setIssuedBy(String issuedBy) {
        this.issuedBy.set( issuedBy );
    }
}

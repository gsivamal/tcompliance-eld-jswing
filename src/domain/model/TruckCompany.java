package domain.model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class TruckCompany {

    private int id;
    private StringProperty DOT = new SimpleStringProperty();
    private StringProperty carrier = new SimpleStringProperty();
    private StringProperty ownerName = new SimpleStringProperty();
    private StringProperty address = new SimpleStringProperty();
    private StringProperty address2 = new SimpleStringProperty();
    private StringProperty city = new SimpleStringProperty();
    private StringProperty state = new SimpleStringProperty();
    private IntegerProperty zip = new SimpleIntegerProperty();
    private StringProperty country = new SimpleStringProperty();

    public TruckCompany(int id, String DOT, String carrier, String ownerName,
                        String address, String address2, String city, String state,
                        int zip, String country) {
        setId( id );
        setDOT( DOT );
        setCarrier( carrier );
        setOwnerName( ownerName );
        setAddress( address );
        setAddress2( address2 );
        setCity( city );
        setState( state );
        setZip( zip );
        setCountry( country );
    }


    public int getId() {
        return id;
    }

    private void setId(int id) {
        this.id = id;
    }

    public String getDOT() {
        return DOT.get();
    }

    public StringProperty DOTProperty() {
        return DOT;
    }

    public void setDOT(String DOT) {
        this.DOT.set( DOT );
    }

    public String getCarrier() {
        return carrier.get();
    }

    public StringProperty carrierProperty() {
        return carrier;
    }

    public void setCarrier(String carrier) {
        this.carrier.set( carrier );
    }

    public String getOwnerName() {
        return ownerName.get();
    }

    public StringProperty ownerNameProperty() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName.set( ownerName );
    }

    public String getAddress() {
        return address.get();
    }

    public StringProperty addressProperty() {
        return address;
    }

    public void setAddress(String address) {
        this.address.set( address );
    }

    public String getAddress2() {
        return address2.get();
    }

    public StringProperty address2Property() {
        return address2;
    }

    public void setAddress2(String address2) {
        this.address2.set( address2 );
    }

    public String getCity() {
        return city.get();
    }

    public StringProperty cityProperty() {
        return city;
    }

    public void setCity(String city) {
        this.city.set( city );
    }

    public String getState() {
        return state.get();
    }

    public StringProperty stateProperty() {
        return state;
    }

    public void setState(String state) {
        this.state.set( state );
    }

    public int getZip() {
        return zip.get();
    }

    public IntegerProperty zipProperty() {
        return zip;
    }

    public void setZip(int zip) {
        this.zip.set( zip );
    }

    public String getCountry() {
        return country.get();
    }

    public StringProperty countryProperty() {
        return country;
    }

    public void setCountry(String country) {
        this.country.set( country );
    }
}

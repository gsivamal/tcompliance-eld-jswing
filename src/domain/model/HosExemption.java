package domain.model;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class HosExemption {

    private int id;
    private StringProperty exemption = new SimpleStringProperty();
    private BooleanProperty status = new SimpleBooleanProperty();

    public HosExemption(int id, String exemption, boolean status) {
        setId( id );
        setExemption( exemption );
        setStatus( status );
    }

    public int getId() {
        return id;
    }

    private void setId(int id) {
        this.id = id;
    }

    public String getExemption() {
        return exemption.get();
    }

    public StringProperty exemptionProperty() {
        return exemption;
    }

    public void setExemption(String exemption) {
        this.exemption.set( exemption );
    }

    public boolean getStatus() {
        return status.get();
    }

    public BooleanProperty statusProperty() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status.set( status );
    }
}

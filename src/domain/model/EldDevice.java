package domain.model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class EldDevice {

    private int id;
    private StringProperty manufacturer = new SimpleStringProperty();
    private StringProperty version = new SimpleStringProperty();

    public EldDevice(int id, String manufacturer, String version) {
        setId( id );
        setManufacturer( manufacturer );
        setVersion( version );
    }

    public int getId() {
        return id;
    }

    private void setId(int id) {
        this.id = id;
    }

    public String getManufacturer() {
        return manufacturer.get();
    }

    public StringProperty manufacturerProperty() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer.set( manufacturer );
    }

    public String getVersion() {
        return version.get();
    }

    public StringProperty versionProperty() {
        return version;
    }

    public void setVersion(String version) {
        this.version.set( version );
    }
}

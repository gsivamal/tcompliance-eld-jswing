package domain.model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class CompanySettings {

    private int id;
    private StringProperty name = new SimpleStringProperty();
    private StringProperty value = new SimpleStringProperty();
    private StringProperty description = new SimpleStringProperty();

    public CompanySettings(int id, String name, String value, String description) {
        setId( id );
        setName( name );
        setValue( value );
        setDescription( description );
    }

    public int getId() {
        return id;
    }

    private void setId(int id) {
        this.id = id;
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

    public String getValue() {
        return value.get();
    }

    public StringProperty valueProperty() {
        return value;
    }

    public void setValue(String value) {
        this.value.set( value );
    }

    public String getDescription() {
        return description.get();
    }

    public StringProperty descriptionProperty() {
        return description;
    }

    public void setDescription(String description) {
        this.description.set( description );
    }
}

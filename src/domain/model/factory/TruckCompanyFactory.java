package domain.model.factory;

import dao.DaoException;
import domain.mediator.Instances;
import domain.model.TruckCompany;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class TruckCompanyFactory extends Factory<TruckCompany>{

    private StringProperty DOT = new SimpleStringProperty();
    private StringProperty carrier = new SimpleStringProperty();
    private StringProperty ownerName = new SimpleStringProperty();
    private StringProperty address = new SimpleStringProperty();
    private StringProperty address2 = new SimpleStringProperty();
    private StringProperty city = new SimpleStringProperty();
    private StringProperty state = new SimpleStringProperty();
    private IntegerProperty zip = new SimpleIntegerProperty();
    private StringProperty country = new SimpleStringProperty();

    private static final TruckCompanyFactory instance = new TruckCompanyFactory();

    private TruckCompanyFactory(){}

    public static TruckCompanyFactory getInstance() {
        return instance;
    }

    public TruckCompany getTruckCompany(String DOT, String carrier, String ownerName,
                                        String address, String address2, String city,
                                        String state, int zip, String country) {
        try {
            incrementCount();
            return getTruckCompany( getCount(), DOT, carrier, ownerName, address,
                    address2, city, state, zip, country );
        } catch (Exception e) {
            decrementCount();
            throw e;
        }
    }

    public TruckCompany getTruckCompany(int id, String DOT, String carrier,
                                        String ownerName, String address,
                                        String address2, String city, String state,
                                        int zip, String country) {
        TruckCompany item = new TruckCompany( id, DOT, carrier, ownerName,
                address, address2, city, state, zip, country );
        cachedObjects.put( id, item );
        return item;
    }

    public TruckCompany getTruckCompany(int id) throws DaoException{
        TruckCompany item = cachedObjects.get( id );
        if ( item == null ) {
            item = Instances.getTruckCompanySQLiteDB().get( id );
            cachedObjects.put( id, item );
        }
        return item;
    }
}

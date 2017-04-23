package domain.model.factory;

import domain.mediator.Instances;
import domain.model.Driver;
import domain.model.FuelCard;
import domain.model.Load;
import domain.model.Vehicle;

import java.sql.SQLException;
import java.time.LocalDateTime;

public class LoadFactory extends Factory<Load> {

    private static final LoadFactory instance = new LoadFactory();

    private LoadFactory(){}

    public static LoadFactory getInstance() {
        return instance;
    }

    public Load getLoad(LocalDateTime startTime, LocalDateTime endTime, Vehicle truck, Vehicle trailer1, Vehicle trailer2, String blNumber, FuelCard fuelCard, Driver driver) {
        try {
            incrementCount();
            return getLoad( getCount(), startTime, endTime, truck, trailer1, trailer2, blNumber, fuelCard, driver );
        } catch (Exception e) {
            decrementCount();
            throw e;
        }
    }

    public Load getLoad(int loadID, LocalDateTime startTime, LocalDateTime endTime, Vehicle truck, Vehicle trailer1, Vehicle trailer2, String blNumber, FuelCard fuelCard, Driver driver) {
        Load load = new Load( loadID, startTime, endTime, truck, trailer1, trailer2, blNumber, fuelCard, driver );
        cachedObjects.put( loadID, load );
        return load;
    }

    public Load getLoad(int loadID) {
        Load load = cachedObjects.get( loadID );
        if ( load == null ) {
            try {
                load = Instances.getLoadDatabaseSQLiteDB().get( loadID );
            } catch (SQLException e) {
                e.printStackTrace();
            }
            cachedObjects.put( loadID, load );
        }
        return load;
    }

}

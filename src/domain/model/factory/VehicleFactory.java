package domain.model.factory;

import dao.VehicleDatabaseDAO;
import domain.model.Vehicle;

public class VehicleFactory extends Factory<Vehicle> {

    private static final VehicleFactory instance = new VehicleFactory();
    private VehicleFactory(){}

    public static VehicleFactory getInstance(){
        return instance;
    }

    public Vehicle getVehicle(String name, String make, String vin) {
        try {
            incrementCount();
            return getVehicle( getCount(), name, make, vin );
        } catch (Exception e) {
            decrementCount();
            throw e;
        }
    }

    public Vehicle getVehicle(int ID, String name, String make, String vin) {
        Vehicle vehicle = new Vehicle( ID, name, make, vin );
        cachedObjects.put( ID, vehicle );
        return vehicle;
    }

    public Vehicle getVehicle(int ID) {
        Vehicle vehicle = cachedObjects.get( ID );
        if ( vehicle == null ) {
            vehicle = VehicleDatabaseDAO.getInstance().get( ID );
            cachedObjects.put( ID, vehicle );
        }
        return vehicle;
    }

}

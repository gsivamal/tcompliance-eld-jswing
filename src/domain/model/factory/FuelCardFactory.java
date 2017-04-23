package domain.model.factory;

import domain.mediator.Instances;
import domain.model.FuelCard;

public class FuelCardFactory extends Factory<FuelCard>{

    private static FuelCardFactory instance = new FuelCardFactory();
    private FuelCardFactory(){}

    public static FuelCardFactory getInstance(){
        return instance;
    }

    public FuelCard getFuelCard(int fuelCardNumber, String issuedBy) {
        try {
            incrementCount();
            return getFuelCard( getCount(), fuelCardNumber, issuedBy );
        } catch (Exception e) {
            decrementCount();
            throw e;
        }
    }

    public FuelCard getFuelCard(int fuelCardID, int fuelCardNumber, String issuedBy) {
        FuelCard fuelCard = new FuelCard( fuelCardID, fuelCardNumber, issuedBy );
        cachedObjects.put( fuelCardID, fuelCard );
        return fuelCard;
    }

    public FuelCard getFuelCard(int fuelCardID){
        FuelCard fuelCard = cachedObjects.get( fuelCardID );
        if ( fuelCard == null ) {
            fuelCard = Instances.getFuelCardSQLiteDB().get( fuelCardID );
            cachedObjects.put( fuelCardID, fuelCard );
        }
        return fuelCard;
    }
}

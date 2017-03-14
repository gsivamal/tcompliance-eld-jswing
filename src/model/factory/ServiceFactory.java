package model.factory;

import model.*;

public class ServiceFactory extends Factory{

    private static final ServiceFactory instance = new ServiceFactory();

    private ServiceFactory(){}

    public static ServiceFactory getInstance(){
        return instance;
    }

    public Service createService(int id, User driver, User coDriver, Fuel fuel, Load load, StatusHistory statusHistory) {
        Status lastStatus = statusHistory.get( statusHistory.size() - 1 );
        statusHistory.removeStatus( statusHistory.size() - 1 );
        return new Service( id, driver, coDriver, fuel, load, statusHistory, lastStatus );
    }

    public Service createService(User driver, User coDriver) {
        try {
            incrementCount();
            return new Service( getCount(), driver, coDriver,
                    new Fuel( 0, "", "" ),
                    new Load( 0, null, null ), new StatusHistory(), StatusFactory.getInstance().createStatus( Status.StatusValue.ON ) );
        } catch (Exception e) {
            decrementCount();
            throw e;
        }
    }
}

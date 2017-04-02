package domain.model.factory;

import dao.DriverDatabaseDAO;
import dao.LogbookDatabaseDAO;
import domain.model.*;

public class DriverFactory extends Factory<Driver> {

    private static final DriverFactory instance = new DriverFactory();

    private DriverFactory() {
    }

    public static DriverFactory getInstance() {
        return instance;
    }

    public Driver getDriver(String username, String password, String confirmPassword, String firstName, String middleName, String lastName, String licNumber, String status, String issuedState, String issuedCountry, boolean isAdmin) {
        try {
            incrementCount();
            LogbookList logbookList = new LogbookList();
            Logbook logbook = LogbookFactory.getInstance().getLogbook( DutyStatus.OFF );
            logbookList.add( logbook );
            Driver driver = getDriver( getCount(), username, password, confirmPassword, firstName, middleName, lastName, licNumber, status, issuedState, issuedCountry, isAdmin, logbookList );
            logbook.setDriver( driver );
            LogbookDatabaseDAO.getInstance().add( logbook );
            return driver;
        } catch (Exception e) {
            decrementCount();
            throw e;
        }
    }

    public Driver getDriver(int id, String username, String password, String confirmPassword, String firstName, String middleName, String lastName, String licNumber, String status, String issuedState, String issuedCountry, boolean isAdmin, LogbookList logbookList) {
        if ( !password.equals( confirmPassword ) ) {
            throw new IllegalArgumentException( "Passwords do not match!" );
        }
        Driver driver = new Driver( id, username, password, firstName, middleName, lastName, licNumber, status, issuedState, issuedCountry, isAdmin, logbookList);
        cachedObjects.put( id, driver );
        return driver;
    }

    public Driver getDriver(int driverID) {
        Driver driver = cachedObjects.get( driverID );
        if ( driver == null ) {
            driver = DriverDatabaseDAO.getInstance().get( driverID );
            cachedObjects.put( driverID, driver );
        }
        return driver;
    }

}
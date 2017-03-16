package model.factory;

import dao.LogbookDatabaseDAO;
import model.*;

import java.time.LocalDateTime;

public class LogbookFactory extends Factory<Logbook> {

    private static final LogbookFactory instance = new LogbookFactory();
    private LogbookFactory(){}

    public static LogbookFactory getInstance(){
        return instance;
    }

    public Logbook getLogbook(DutyStatus dutyStatus, LocalDateTime startTime, Driver driver, GPSLocation gpsLocation) {
        try {
            incrementCount();
            return getLogbook( getCount(), dutyStatus, startTime, startTime, driver, gpsLocation, LogbookStatus.WAITING, startTime, 0, "", "" );
        } catch (Exception e) {
            decrementCount();
            throw e;
        }
    }

    public Logbook getLogbook(DutyStatus dutyStatus) {
        return getLogbook( dutyStatus, LocalDateTime.now(), Mediator.getInstance().getLoggedDriver(), GPSLocation.getLatestGPSLocation() );
    }

    public Logbook getLogbook(int ID, DutyStatus dutyStatus, LocalDateTime startTime, LocalDateTime endTime, Driver driver,
                   GPSLocation gpsLocation, LogbookStatus approvedStatus, LocalDateTime approvedTime, int mileage, String additionalInfo, String notes) {
        Logbook logbook = new Logbook( ID, dutyStatus, startTime, endTime, driver, gpsLocation, approvedStatus, approvedTime, mileage, additionalInfo, notes );
        cachedObjects.put( ID, logbook );
        return logbook;
    }

    public Logbook getLogbook(int ID) {
        Logbook logbook = cachedObjects.get( ID );
        if ( logbook == null ) {
            logbook = LogbookDatabaseDAO.getInstance().get( ID );
            cachedObjects.put( ID, logbook );
        }
        return logbook;
    }

}

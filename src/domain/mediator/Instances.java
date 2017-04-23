package domain.mediator;

import dao.*;
import domain.model.HosExemption;
import domain.model.factory.HosExemptionFactory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Instances {
    private static final CompanySettingsSQLiteDB companySettingsSQLiteDB =
            new CompanySettingsSQLiteDB();
    private static final EldDeviceSQLiteDB eldDeviceSQLiteDB =
            new EldDeviceSQLiteDB();
    private static final TruckCompanySQLiteDB truckCompanySQLiteDB =
            new TruckCompanySQLiteDB();
    private static final HosExemptionSQLiteDB hosExemptionSQLiteDB =
            new HosExemptionSQLiteDB();
    private static final DriverDatabaseDAO driverSQLiteDB =
            new DriverDatabaseDAO();
    private static final DutyStatusDatabaseDAO dutyStatusSQLiteDB =
            new DutyStatusDatabaseDAO();
    private static final LoadDatabaseDAO loadDatabaseSQLiteDB =
            new LoadDatabaseDAO();
    private static final LogbookDatabaseDAO logbookSQLiteDB =
            new LogbookDatabaseDAO();
    private static final LogbookStatusDatabaseDAO logbookStatusSQLiteDB =
            new LogbookStatusDatabaseDAO();
    private static final VehicleDatabaseDAO vehicleSQLiteDb =
            new VehicleDatabaseDAO();
    private static final FuelCardDatabaseDAO fuelCardSQLiteDB =
            new FuelCardDatabaseDAO();

    private static final List<HosExemption> defaultHosExemptions =
            new ArrayList<>(3);

    static{
        HosExemptionFactory hosExemptionFactory = HosExemptionFactory.getInstance();
        defaultHosExemptions.addAll( Arrays.asList(
                hosExemptionFactory.
                        getHosExemption( "14-Hour rule", false ),
                hosExemptionFactory.
                        getHosExemption( "11-Hour Rule", false ),
                hosExemptionFactory.
                        getHosExemption( "30-Minute Break Rule", false )
        ) );
    }

    public static CompanySettingsSQLiteDB getCompanySettingsSQLiteDB() {
        return companySettingsSQLiteDB;
    }

    public static EldDeviceSQLiteDB getEldDeviceSQLiteDB() {
        return eldDeviceSQLiteDB;
    }

    public static TruckCompanySQLiteDB getTruckCompanySQLiteDB() {
        return truckCompanySQLiteDB;
    }

    public static HosExemptionSQLiteDB getHosExemptionSQLiteDB() {
        return hosExemptionSQLiteDB;
    }

    public static DriverDatabaseDAO getDriverSQLiteDB() {
        return driverSQLiteDB;
    }

    public static DutyStatusDatabaseDAO getDutyStatusSQLiteDB() {
        return dutyStatusSQLiteDB;
    }

    public static LoadDatabaseDAO getLoadDatabaseSQLiteDB() {
        return loadDatabaseSQLiteDB;
    }

    public static LogbookDatabaseDAO getLogbookSQLiteDB() {
        return logbookSQLiteDB;
    }

    public static LogbookStatusDatabaseDAO getLogbookStatusSQLiteDB() {
        return logbookStatusSQLiteDB;
    }

    public static VehicleDatabaseDAO getVehicleSQLiteDb() {
        return vehicleSQLiteDb;
    }

    public static FuelCardDatabaseDAO getFuelCardSQLiteDB() {
        return fuelCardSQLiteDB;
    }

    public static List<HosExemption> getDefaultHosExemptions() {
        return defaultHosExemptions;
    }
}

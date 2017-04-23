package dao;

import domain.mediator.Instances;
import domain.model.HosExemption;

import java.sql.*;

public class SQLiteDatabase {

    private static Connection connection;

    public static Connection getConnection() throws SQLException{
        if ( connection == null ) {
            try {
                Class.forName("org.sqlite.JDBC");
                connection = DriverManager.getConnection( "jdbc:sqlite:logbook.db" );
                System.out.println("Connection opened successfully!");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return connection;
    }


    public static int getLastID(String table, String IDColumnName){
        try {
            Connection connection = SQLiteDatabase.getConnection();
            String sql = String.format(
                    "SELECT %s " +
                            "FROM %s " +
                            "ORDER BY %s " +
                            "DESC LIMIT 1;"
                    , IDColumnName, table, IDColumnName );
            PreparedStatement getLastID = connection.prepareStatement( sql );
            ResultSet resultSet = getLastID.executeQuery();
            if ( resultSet.next() ) {
                return resultSet.getInt( IDColumnName );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }
    public static void initializeTables(Connection connection) throws DaoException {

        try {
            PreparedStatement driverTableStatement = connection.prepareStatement(
                    "CREATE TABLE IF NOT EXISTS driver (" +
                            "driver_id INT PRIMARY KEY NOT NULL," +
                            "first_name TEXT NOT NULL," +
                            "middle_name TEXT," +
                            "last_name TEXT NOT NULL," +
                            "lic_number INT NOT NULL ," +
                            "issued_country TEXT NOT NULL," +
                            "issued_state TEXT NOT NULL," +
                            "status TEXT NOT NULL," +
                            "username TEXT NOT NULL," +
                            "password TEXT NOT NULL," +
                            "is_admin INT NOT NULL" +
                            ");" );
            driverTableStatement.executeUpdate();

            PreparedStatement equipmentTableStatement = connection.prepareStatement(
                    "CREATE TABLE IF NOT EXISTS equipment(" +
                            "equipment_id INTEGER PRIMARY KEY," +
                            "equipment_name TEXT NOT NULL," +
                            "equipment_make TEXT NOT NULL," +
                            "equipment_vin TEXT NOT NULL" +
                            ");" );
            equipmentTableStatement.executeUpdate();

            PreparedStatement fuelCardTableStatement = connection.prepareStatement(
                    "CREATE TABLE IF NOT EXISTS fuel_card(" +
                            "fuel_card_id INTEGER PRIMARY KEY," +
                            "fuel_card_number INTEGER NOT NULL," +
                            "fuel_card_issued_by TEXT NOT NULL" +
                            ");" );
            fuelCardTableStatement.executeUpdate();

            PreparedStatement logbookStatusTableStatement = connection.prepareStatement(
                    "CREATE TABLE IF NOT EXISTS logbook_status (" +
                            "logbook_status_id INTEGER PRIMARY KEY," +
                            "logbook_status_value TEXT NOT NULL" +
                            ");" );
            logbookStatusTableStatement.executeUpdate();

            PreparedStatement dutyStatusTableStatement = connection.prepareStatement(
                    "CREATE TABLE IF NOT EXISTS duty_status(" +
                            "duty_status_id INTEGER PRIMARY KEY," +
                            "duty_status_value TEXT NOT NULL" +
                            ");" );
            dutyStatusTableStatement.executeUpdate();

            PreparedStatement loadTableStatement = connection.prepareStatement(
                    "CREATE TABLE IF NOT EXISTS load (" +
                            "load_id INT PRIMARY KEY," +
                            "start_time TIMESTAMP NOT NULL," +
                            "end_time TIMESTAMP NOT NULL," +
                            "truck_id INTEGER NOT NULL," +
                            "trailer1_id INTEGER NOT NULL," +
                            "trailer2_id INTEGER NOT NULL," +
                            "bl_number TEXT NOT NULL," +
                            "fuel_card_id INTEGER NOT NULL," +
                            "driver_id INTEGER NOT NULL," +
                            "FOREIGN KEY (truck_id) REFERENCES equipment(equipment_id)," +
                            "FOREIGN KEY (trailer1_id) REFERENCES equipment(equipment_id)," +
                            "FOREIGN KEY (trailer2_id) REFERENCES equipment(equipment_id)," +
                            "FOREIGN KEY (fuel_card_id) REFERENCES fuel_card(fuel_card_id)," +
                            "FOREIGN KEY (driver_id) REFERENCES driver(driver_id)" +
                            ");" );
            loadTableStatement.executeUpdate();

            PreparedStatement gpsLocationTableStatement = connection.prepareStatement(
                    "CREATE TABLE IF NOT EXISTS gps_location(" +
                            "location TEXT NOT NULL," +
                            "latitude REAL NOT NULL," +
                            "longitude REAL NOT NULL," +
                            "created_date DATETIME NOT NULL" +
                            ");" );
            gpsLocationTableStatement.executeUpdate();

            PreparedStatement logbookTableStatement = connection.prepareStatement(
                    "CREATE TABLE IF NOT EXISTS logbook(" +
                            "logbook_id INTEGER PRIMARY KEY," +
                            "status_id INTEGER NOT NULL," +
                            "start_time TIMESTAMP NOT NULL," +
                            "end_time TIMESTAMP NOT NULL," +
                            "driver_id INTEGER NOT NULL," +
                            "location TEXT NOT NULL," +
                            "latitude REAL NOT NULL," +
                            "longitude REAL NOT NULL," +
                            "approve_status_id INTEGER NOT NULL," +
                            "approved_date TIMESTAMP NOT NULL," +
                            "mileage INTEGER NOT NULL," +
                            "add_info TEXT," +
                            "notes TEXT," +
                            "FOREIGN KEY (status_id) REFERENCES duty_status(duty_status_id)," +
                            "FOREIGN KEY (driver_id) REFERENCES driver(driver_id)," +
                            "FOREIGN KEY (approve_status_id) REFERENCES logbook_status(logbook_status_id)" +
                            ");" );
            logbookTableStatement.executeUpdate();


            PreparedStatement companySettingsTableStatement = connection.prepareStatement(
                    "CREATE TABLE IF NOT EXISTS company_settings(" +
                            "id INTEGER PRIMARY KEY," +
                            "name TEXT NOT NULL," +
                            "value TEXT NOT NULL," +
                            "description TEXT" +
                            ");"
            );
            companySettingsTableStatement.execute();

            PreparedStatement truckCompanyTableStatement = connection.prepareStatement(
                    "CREATE TABLE IF NOT EXISTS truck_company(" +
                            "id INTEGER PRIMARY KEY," +
                            "DOT INTEGER NOT NULL," +
                            "carrier TEXT NOT NULL," +
                            "owner_name TEXT NOT NULL," +
                            "address TEXT NOT NULL," +
                            "address2 TEXT NOT NULL," +
                            "city TEXT NOT NULL," +
                            "state TEXT NOT NULL," +
                            "zip INTEGER NOT NULL," +
                            "country TEXT NOT NULL" +
                            ");"
            );
            truckCompanyTableStatement.execute();

            PreparedStatement eldDeviceTableStatement = connection.prepareStatement(
                    "CREATE TABLE IF NOT EXISTS eld_device(" +
                            "id INTEGER PRIMARY KEY," +
                            "manufacturer TEXT NOT NULL," +
                            "version TEXT NOT NULL" +
                            ");"
            );
            eldDeviceTableStatement.execute();

            PreparedStatement hosExemptionsTableStatement = connection.prepareStatement(
                    "CREATE TABLE IF NOT EXISTS hos_exemptions(" +
                            "id INTEGER PRIMARY KEY," +
                            "exemption TEXT NOT NULL," +
                            "status INTEGER NOT NULL" +
                            ");"
            );
            hosExemptionsTableStatement.execute();

            HosExemptionSQLiteDB hosExemptionSQLiteDB = Instances.getHosExemptionSQLiteDB();
            int hosExemptionSize = hosExemptionSQLiteDB.getAll().size();
            if ( hosExemptionSize == 0 ) {
                for (HosExemption hosExemption : Instances.getDefaultHosExemptions()) {
                    hosExemptionSQLiteDB.add( hosExemption );
                }
            }

        } catch (SQLException e) {
            throw new DaoException( DaoExceptionType.SQL, e.getMessage() );
        }
    }
}

package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DbUtil {

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

    public static void initializeTables(Connection connection) throws SQLException {
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

        PreparedStatement serviceTableStatement = connection.prepareStatement( "CREATE TABLE IF NOT EXISTS service (" +
                "service_id INT PRIMARY KEY," +
                "vehicle_id INT NOT NULL," +
                "driver_id INT NOT NULL," +
                "co_driver_id INT," +
                "rule1 TEXT," +
                "rule2 TEXT," +
                "rule3 TEXT," +
                "rule4 TEXT," +
                "FOREIGN KEY (driver_id) REFERENCES driver(driver_id)" +
                ");" );
        serviceTableStatement.executeUpdate();

        PreparedStatement statusTableStatement = connection.prepareStatement( "CREATE TABLE IF NOT EXISTS status (" +
                "status_id INT PRIMARY KEY," +
                "service_id INT NOT NULL," +
                "status_value TEXT NOT NULL," +
                "start_time DATETIME NOT NULL," +
                "end_time DATETIME," +
                "FOREIGN KEY (service_id) REFERENCES service(service_id)" +
                ");" );
        statusTableStatement.executeUpdate();

        PreparedStatement fuelTableStatement = connection.prepareStatement( "CREATE TABLE IF NOT EXISTS fuel (" +
                "service_id INT NOT NULL," +
                "trailer1_status TEXT NOT NULL," +
                "trailer2_status TEXT NOT NULL," +
                "fuel_card_number INT NOT NULL," +
                "FOREIGN KEY (service_id) REFERENCES service(service_id)" +
                ");" );
        fuelTableStatement.executeUpdate();

        PreparedStatement loadTableStatement = connection.prepareStatement( "CREATE TABLE IF NOT EXISTS load (" +
                "load_number INT PRIMARY KEY," +
                "service_id INT NOT NULL," +
                "start_time INT NOT NULL," +
                "end_time INT NOT_NULL," +
                "FOREIGN KEY (service_id) REFERENCES service(service_id)" +
                ");" );
        loadTableStatement.executeUpdate();
    }
}

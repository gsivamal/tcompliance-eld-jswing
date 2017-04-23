package domain.model;

import dao.SQLiteDatabase;
import javafx.beans.property.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;

public class GPSLocation {

    private DoubleProperty longitude = new SimpleDoubleProperty();
    private DoubleProperty latitude = new SimpleDoubleProperty();
    private StringProperty location = new SimpleStringProperty();

    public GPSLocation(double longitude, double latitude, String location) {
        setLongitude( longitude );
        setLatitude( latitude );
        setLocation( location );
    }

    public double getLongitude() {
        return longitude.get();
    }

    public DoubleProperty longitudeProperty() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude.set( longitude );
    }

    public double getLatitude() {
        return latitude.get();
    }

    public DoubleProperty latitudeProperty() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude.set( latitude );
    }

    public String getLocation() {
        return location.get();
    }

    public StringProperty locationProperty() {
        return location;
    }

    public void setLocation(String location) {
        this.location.set( location );
    }

    @Override
    public String toString() {
        return String.format( "%s ( %.2f, %.2f)", getLocation(), getLatitude(), getLongitude() );
    }

    public static GPSLocation getLatestGPSLocation(){
        try {
            Connection connection = SQLiteDatabase.getConnection();
            PreparedStatement getStatement = connection.prepareStatement(
                    "SELECT longitude, latitude, location " +
                            "FROM gps_location"
            );
            ResultSet resultSet = getStatement.executeQuery();
            if ( resultSet.next() ) {
                double longitude = resultSet.getLong( "longitude" );
                double latitude = resultSet.getLong( "latitude" );
                String location = resultSet.getString( "location" );
                return new GPSLocation( longitude, latitude, location );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void updateLatestGPSLocation(String location, double latitude, double longitude){
        try {
            Connection connection = SQLiteDatabase.getConnection();
            PreparedStatement truncateStatement = connection.prepareStatement( "DELETE FROM gps_location;" );
            truncateStatement.executeUpdate();
            truncateStatement.close();
            PreparedStatement updateStatement = connection.prepareStatement(
                    "INSERT INTO gps_location " +
                    "(location, latitude, longitude, created_date) " +
                    "VALUES (?, ?, ?, ?);"
            );
            updateStatement.setString( 1, location );
            updateStatement.setDouble( 2, latitude );
            updateStatement.setDouble( 3, longitude );
            updateStatement.setString( 4, LocalDateTime.now().format( Clock.formatter ) );
            updateStatement.executeUpdate();
            updateStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}

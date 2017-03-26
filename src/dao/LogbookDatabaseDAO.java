package dao;

import model.*;
import model.factory.DriverFactory;
import model.factory.LogbookFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;

public class LogbookDatabaseDAO implements LogbookDao{

    private static final LogbookDatabaseDAO instance = new LogbookDatabaseDAO();
    private LogbookDatabaseDAO(){}

    public static LogbookDatabaseDAO getInstance(){
        return instance;
    }

    @Override
    public void add(Logbook item) {
        try {
            Connection connection = DbUtil.getConnection();
            PreparedStatement addStatement = connection.prepareStatement(
                    "INSERT INTO logbook " +
                            "(status_id, start_time, end_time, driver_id, " +
                            "location, latitude, longitude, approve_status_id, approved_date, " +
                            "mileage, add_info, notes, logbook_id) " +
                            "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);"
            );
            addStatement.setInt( 1, item.getDutyStatus().getID() );
            addStatement.setString( 2, item.getStartTime().format( Clock.formatter ) );
            addStatement.setString( 3, item.getEndTime().format( Clock.formatter ) );
            addStatement.setInt( 4, item.getDriver().getID() );
            addStatement.setString( 5, item.getGpsLocation().getLocation() );
            addStatement.setDouble( 6, item.getGpsLocation().getLatitude() );
            addStatement.setDouble( 7, item.getGpsLocation().getLongitude() );
            addStatement.setInt( 8, item.getApprovedStatus().getID() );
            addStatement.setString( 9, item.getApprovedTime().format( Clock.formatter ) );
            addStatement.setInt( 10, item.getMileage() );
            addStatement.setString( 11, item.getAdditionalInfo() );
            addStatement.setString( 12, item.getNotes() );
            addStatement.setInt( 13, item.getID() );
            addStatement.executeUpdate();
            addStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(Logbook item) {
        try {
            Connection connection = DbUtil.getConnection();
            PreparedStatement updateStatement = connection.prepareStatement(
                    "UPDATE logbook " +
                            "SET status_id = ?, start_time = ?, end_time = ?, driver_id = ?, location = ?, latitude = ?, longitude = ?, " +
                            "approve_status_id = ?, approved_date = ?, mileage = ?, add_info = ?, notes = ? " +
                            "WHERE logbook_id = ?"
            );
            updateStatement.setInt( 1, item.getDutyStatus().getID() );
            updateStatement.setString( 2, item.getStartTime().format( Clock.formatter ) );
            updateStatement.setString( 3, item.getEndTime().format( Clock.formatter ) );
            updateStatement.setInt( 4, item.getDriver().getID() );
            updateStatement.setString( 5, item.getGpsLocation().getLocation() );
            updateStatement.setDouble( 6, item.getGpsLocation().getLatitude() );
            updateStatement.setDouble( 7, item.getGpsLocation().getLongitude() );
            updateStatement.setInt( 8, item.getApprovedStatus().getID() );
            updateStatement.setString( 9, item.getApprovedTime().format( Clock.formatter ) );
            updateStatement.setInt( 10, item.getMileage() );
            updateStatement.setString( 11, item.getAdditionalInfo() );
            updateStatement.setString( 12, item.getNotes() );
            updateStatement.setInt( 13, item.getID() );
            updateStatement.executeUpdate();
            updateStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void remove(Logbook item) {
        try {
            Connection connection = DbUtil.getConnection();
            PreparedStatement removeStatement = connection.prepareStatement(
                    "DELETE FROM logbook " +
                            "WHERE logbook_id = ?"
            );
            removeStatement.setInt( 1, item.getID() );
            removeStatement.executeUpdate();
            removeStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @Override
    public Logbook get(int id) {
        try {
            Connection connection = DbUtil.getConnection();
            PreparedStatement getStatement = connection.prepareStatement(
                    "SELECT * FROM logbook WHERE logbook_id = ?"
            );
            getStatement.setInt( 1, id );
            ResultSet resultSet = getStatement.executeQuery();
            getStatement.close();
            if ( resultSet.next() ) {
                DutyStatus dutyStatus = DutyStatus.getByID( resultSet.getInt( "status_id" ) );
                LocalDateTime startTime = LocalDateTime.parse( resultSet.getString( "start_time" ), Clock.formatter );
                LocalDateTime endTime = LocalDateTime.parse( resultSet.getString( "end_time" ), Clock.formatter );
                Driver driver = DriverFactory.getInstance().getDriver( resultSet.getInt( "driver_id" ) );
                String location = resultSet.getString( "location" );
                double latitude = resultSet.getDouble( "latitude" );
                double longitude = resultSet.getDouble( "longitude" );
                LogbookStatus approveStatus = LogbookStatus.getByID( resultSet.getInt( "approve_status_id" ) );
                LocalDateTime approveTime = LocalDateTime.parse( resultSet.getString( "approved_date" ), Clock.formatter );
                int mileage = resultSet.getInt( "mileage" );
                String additionalInfo = resultSet.getString( "add_info" );
                String notes = resultSet.getString( "notes" );
                GPSLocation gpsLocation = new GPSLocation( longitude, latitude, location );
                Logbook logbook = LogbookFactory.getInstance().getLogbook( id, dutyStatus, startTime, endTime, driver, gpsLocation,
                        approveStatus, approveTime, mileage, additionalInfo, notes );
                return logbook;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public LogbookList getAll() {
        LogbookList logbookList = new LogbookList();
        try {
            Connection connection = DbUtil.getConnection();
            PreparedStatement getStatement = connection.prepareStatement(
                    "SELECT * FROM logbook;"
            );
            ResultSet resultSet = getStatement.executeQuery();
            while ( resultSet.next() ) {
                int id = resultSet.getInt( "logbook_id" );
                DutyStatus dutyStatus = DutyStatus.getByID( resultSet.getInt( "status_id" ) );
                LocalDateTime startTime = LocalDateTime.parse( resultSet.getString( "start_time" ), Clock.formatter );
                LocalDateTime endTime = LocalDateTime.parse( resultSet.getString( "end_time" ), Clock.formatter );
                Driver driver = DriverFactory.getInstance().getDriver( resultSet.getInt( "driver_id" ) );
                String location = resultSet.getString( "location" );
                double latitude = resultSet.getDouble( "latitude" );
                double longitude = resultSet.getDouble( "longitude" );
                LogbookStatus approveStatus = LogbookStatus.getByID( resultSet.getInt( "approve_status_id" ) );
                LocalDateTime approveTime = LocalDateTime.parse( resultSet.getString( "approved_date" ), Clock.formatter );
                int mileage = resultSet.getInt( "mileage" );
                String additionalInfo = resultSet.getString( "add_info" );
                String notes = resultSet.getString( "notes" );
                GPSLocation gpsLocation = new GPSLocation( longitude, latitude, location );
                Logbook logbook = LogbookFactory.getInstance().getLogbook( id, dutyStatus, startTime, endTime, driver, gpsLocation,
                        approveStatus, approveTime, mileage, additionalInfo, notes );
                logbookList.add( logbook );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return logbookList;
    }

    @Override
    public LogbookList getAllByDriverID(int driverID) {
        LogbookList logbookList = new LogbookList();
        try {
            Connection connection = DbUtil.getConnection();
            PreparedStatement getStatement = connection.prepareStatement(
                    "SELECT * FROM logbook WHERE driver_id = ?;"
            );
            getStatement.setInt( 1, driverID);
            ResultSet resultSet = getStatement.executeQuery();
            while ( resultSet.next() ) {
                int id = resultSet.getInt( "logbook_id" );
                DutyStatus dutyStatus = DutyStatus.getByID( resultSet.getInt( "status_id" ) );
                LocalDateTime startTime = LocalDateTime.parse( resultSet.getString( "start_time" ), Clock.formatter );
                LocalDateTime endTime = LocalDateTime.parse( resultSet.getString( "end_time" ), Clock.formatter );
                Driver driver = DriverFactory.getInstance().getDriver( resultSet.getInt( "driver_id" ) );
                String location = resultSet.getString( "location" );
                double latitude = resultSet.getDouble( "latitude" );
                double longitude = resultSet.getDouble( "longitude" );
                LogbookStatus approveStatus = LogbookStatus.getByID( resultSet.getInt( "approve_status_id" ) );
                LocalDateTime approveTime = LocalDateTime.parse( resultSet.getString( "approved_date" ), Clock.formatter );
                int mileage = resultSet.getInt( "mileage" );
                String additionalInfo = resultSet.getString( "add_info" );
                String notes = resultSet.getString( "notes" );
                GPSLocation gpsLocation = new GPSLocation( longitude, latitude, location );
                Logbook logbook = LogbookFactory.getInstance().getLogbook( id, dutyStatus, startTime, endTime, driver, gpsLocation,
                        approveStatus, approveTime, mileage, additionalInfo, notes );
                logbookList.add( logbook );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return logbookList;
    }

    @Override
    public int getLastID() throws SQLException {
        return DbUtil.getLastID( "logbook", "logbook_id" );
    }
}

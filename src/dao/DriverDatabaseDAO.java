package dao;

import model.Driver;
import model.DriverList;
import model.LogbookList;
import model.factory.DriverFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DriverDatabaseDAO implements DriverDao {

    private static final DriverDatabaseDAO instance = new DriverDatabaseDAO();

    private DriverDatabaseDAO(){}

    public static DriverDatabaseDAO getInstance(){
        return instance;
    }

    @Override
    public void add(Driver driver) {
        try {
            Connection connection = DbUtil.getConnection();
            PreparedStatement addStatement = connection.prepareStatement( "INSERT INTO driver " +
                    "(driver_id, first_name, middle_name, last_name, lic_number, issued_country, issued_state, status, username, password, is_admin)" +
                    "VALUES" +
                    "(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);" );
            addStatement.setInt( 1, driver.getID() );
            addStatement.setString( 2, driver.getFirstName() );
            addStatement.setString( 3, driver.getMiddleName() );
            addStatement.setString( 4, driver.getLastName() );
            addStatement.setInt( 5, Integer.parseInt( driver.getLicNumber() ) );
            addStatement.setString( 6, driver.getIssuedCountry() );
            addStatement.setString( 7, driver.getIssuedState() );
            addStatement.setString( 8, driver.getStatus() );
            addStatement.setString( 9, driver.getUsername() );
            addStatement.setString( 10, driver.getPassword() );
            addStatement.setInt( 11, driver.isAdmin() ? 1 : 0 );
            addStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void update(Driver driver) {
        try {
            Connection connection = DbUtil.getConnection();
            PreparedStatement updateStatement = connection.prepareStatement( "UPDATE driver SET " +
                    "first_name = ?, middle_name = ?, last_name = ?, lic_number = ?, issued_country = ?, issued_state = ?, status = ?, username = ?, password = ?, is_admin = ?" +
                    "WHERE driver_id = ?" );
            updateStatement.setString( 1, driver.getFirstName() );
            updateStatement.setString( 2, driver.getMiddleName() );
            updateStatement.setString( 3, driver.getLastName() );
            updateStatement.setInt( 4, Integer.parseInt( driver.getLicNumber() ) );
            updateStatement.setString( 5, driver.getIssuedCountry() );
            updateStatement.setString( 6, driver.getIssuedState() );
            updateStatement.setString( 7, driver.getStatus() );
            updateStatement.setString( 8, driver.getUsername() );
            updateStatement.setString( 9, driver.getPassword() );
            updateStatement.setInt( 10, driver.isAdmin() ? 1 : 0 );
            updateStatement.setInt( 11, driver.getID() );
            updateStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void remove(Driver driver) {
        try {
            Connection connection = DbUtil.getConnection();
            PreparedStatement deleteStatement = connection.prepareStatement( "DELETE FROM driver WHERE driver_id = ?" );
            deleteStatement.setInt( 1, driver.getID() );
            deleteStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Driver get(int id) {
        try {
            Connection connection = DbUtil.getConnection();
            PreparedStatement getStatement = connection.prepareStatement( "SELECT * FROM driver WHERE driver_id = ?" );
            getStatement.setInt( 1, id );
            ResultSet resultSet = getStatement.executeQuery();
            if ( resultSet.next() ) {
                int driverID = resultSet.getInt( "driver_id" );
                String firstName = resultSet.getString( "first_name" );
                String middleName = resultSet.getString( "middle_name" );
                String lastName = resultSet.getString( "last_name" );
                String licNumber = String.valueOf( resultSet.getInt( "lic_number" ) );
                String issuedCountry = resultSet.getString( "issued_country" );
                String issuedState = resultSet.getString( "issued_state" );
                String status = resultSet.getString( "status" );
                String username = resultSet.getString( "username" );
                String password = resultSet.getString( "password" );
                boolean isAdmin = resultSet.getInt( "is_admin" ) == 1;
                Driver driver = DriverFactory.getInstance().getDriver( driverID, username, password, password, firstName, middleName, lastName, licNumber, status, issuedState, issuedCountry, isAdmin, new LogbookList());
                LogbookList logbookList = LogbookDatabaseDAO.getInstance().getAllByDriverID( driverID );
                driver.setLogbookList( logbookList );
                return driver;
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    @Override
    public DriverList getAll() {
        DriverList driverList = new DriverList();
        try {
            Connection connection = DbUtil.getConnection();
            PreparedStatement getAllStatement = connection.prepareStatement( "SELECT * FROM driver" );
            ResultSet resultSet = getAllStatement.executeQuery();
            while ( resultSet.next() ) {
                int driverID = resultSet.getInt( "driver_id" );
                String firstName = resultSet.getString( "first_name" );
                String middleName = resultSet.getString( "middle_name" );
                String lastName = resultSet.getString( "last_name" );
                String licNumber = String.valueOf( resultSet.getInt( "lic_number" ) );
                String issuedCountry = resultSet.getString( "issued_country" );
                String issuedState = resultSet.getString( "issued_state" );
                String status = resultSet.getString( "status" );
                String username = resultSet.getString( "username" );
                String password = resultSet.getString( "password" );
                boolean isAdmin = resultSet.getInt( "is_admin" ) == 1;
                Driver driver = DriverFactory.getInstance().getDriver( driverID, username, password, password, firstName, middleName, lastName, licNumber, status, issuedState, issuedCountry, isAdmin, new LogbookList() );
                LogbookList logbookList = LogbookDatabaseDAO.getInstance().getAllByDriverID( driverID );
                driver.setLogbookList( logbookList );
                driverList.add( driver );
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return driverList;
    }

    public int getCount(){
        try {
            Connection connection = DbUtil.getConnection();
            PreparedStatement getCountStatement = connection.prepareStatement( "SELECT COUNT(*) FROM driver" );
            ResultSet resultSet = getCountStatement.executeQuery();
            if ( resultSet.next() ) {
                return resultSet.getInt( 1 );
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return 0;
    }
}

package dao;

import model.User;
import model.UserList;
import model.factory.UserFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DriverDatabaseDAO implements DriverDao {


    @Override
    public void add(User user) {
        try {
            Connection connection = DbUtil.getConnection();
            PreparedStatement addStatement = connection.prepareStatement( "INSERT INTO driver " +
                    "(driver_id, first_name, middle_name, last_name, lic_number, issued_country, issued_state, status, username, password, is_admin)" +
                    "VALUES" +
                    "(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);" );
            addStatement.setInt( 1, Integer.parseInt( user.getID() ) );
            addStatement.setString( 2, user.getFirstName() );
            addStatement.setString( 3, user.getMiddleName() );
            addStatement.setString( 4, user.getLastName() );
            addStatement.setInt( 5, Integer.parseInt( user.getLicNumber() ) );
            addStatement.setString( 6, user.getIssuedCountry() );
            addStatement.setString( 7, user.getIssuedState() );
            addStatement.setString( 8, user.getStatus() );
            addStatement.setString( 9, user.getUsername() );
            addStatement.setString( 10, user.getPassword() );
            addStatement.setInt( 11, user.isAdmin() ? 1 : 0 );
            addStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void update(User user) {
        try {
            Connection connection = DbUtil.getConnection();
            PreparedStatement updateStatement = connection.prepareStatement( "UPDATE driver SET " +
                    "first_name = ?, middle_name = ?, last_name = ?, lic_number = ?, issued_country = ?, issued_state = ?, status = ?, username = ?, password = ?, is_admin = ?" +
                    "WHERE driver_id = ?" );
            updateStatement.setString( 1, user.getFirstName() );
            updateStatement.setString( 2, user.getMiddleName() );
            updateStatement.setString( 3, user.getLastName() );
            updateStatement.setInt( 4, Integer.parseInt( user.getLicNumber() ) );
            updateStatement.setString( 5, user.getIssuedCountry() );
            updateStatement.setString( 6, user.getIssuedState() );
            updateStatement.setString( 7, user.getStatus() );
            updateStatement.setString( 8, user.getUsername() );
            updateStatement.setString( 9, user.getPassword() );
            updateStatement.setInt( 10, user.isAdmin() ? 1 : 0 );
            updateStatement.setInt( 11, Integer.parseInt( user.getID() ) );
            updateStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void remove(User user) {
        try {
            Connection connection = DbUtil.getConnection();
            PreparedStatement deleteStatement = connection.prepareStatement( "DELETE FROM driver WHERE driver_id = ?" );
            deleteStatement.setInt( 1, Integer.parseInt( user.getID() ) );
            deleteStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public User get(int id) {
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
                return UserFactory.getInstance().createUser( driverID, username, password, password, firstName, middleName, lastName, licNumber, status, issuedState, issuedCountry, isAdmin, null );
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    @Override
    public UserList getAll() {
        UserList userList = new UserList();
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
                User user = UserFactory.getInstance().createUser( driverID, username, password, password, firstName, middleName, lastName, licNumber, status, issuedState, issuedCountry, isAdmin, null );
                userList.add( user );
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return userList;
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

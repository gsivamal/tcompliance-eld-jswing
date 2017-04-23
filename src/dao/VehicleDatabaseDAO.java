package dao;

import domain.model.Vehicle;
import domain.model.VehicleList;
import domain.model.factory.VehicleFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class VehicleDatabaseDAO implements VehicleDao{

    @Override
    public void add(Vehicle item) {
        try {
            Connection connection = SQLiteDatabase.getConnection();
            PreparedStatement addStatement = connection.prepareStatement(
                    "INSERT INTO equipment " +
                            "(equipment_name, equipment_make, equipment_vin, equipment_id) " +
                            "VALUES (?, ?, ?, ?);"
            );
            addStatement.setString( 1, item.getName() );
            addStatement.setString( 2, item.getMake() );
            addStatement.setString( 3, item.getVin() );
            addStatement.setInt( 4, item.getID() );
            addStatement.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void update(Vehicle item) {
        try {
            Connection connection = SQLiteDatabase.getConnection();
            PreparedStatement updateStatement = connection.prepareStatement(
                    "UPDATE equipment SET " +
                            "equipment_name = ?, equipment_make = ?, equipment_vin = ? " +
                            "WHERE equipment_id = ?;"
            );
            updateStatement.setString( 1, item.getName() );
            updateStatement.setString( 2, item.getMake() );
            updateStatement.setString( 3, item.getVin() );
            updateStatement.setInt( 4, item.getID() );
            updateStatement.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void remove(Vehicle item) {
        try {
            Connection connection = SQLiteDatabase.getConnection();
            PreparedStatement deleteStatement = connection.prepareStatement(
                    "DELETE FROM equipment " +
                            "WHERE equipment_id = ?;"
            );
            deleteStatement.setInt( 1, item.getID() );
            deleteStatement.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public Vehicle get(int id) {
        try {
            Connection connection = SQLiteDatabase.getConnection();
            PreparedStatement getStatement = connection.prepareStatement(
                    "SELECT equipment_name, equipment_make, equipment_vin " +
                            "FROM equipment " +
                            "WHERE equipment_id = ?;"
            );
            getStatement.setInt( 1, id );
            ResultSet resultSet = getStatement.executeQuery();
            if ( resultSet.next() ) {
                String equipmentName = resultSet.getString( "equipment_name" );
                String equipmentMake = resultSet.getString( "equipment_make" );
                String equipmentVin = resultSet.getString( "equipment_vin" );
                return VehicleFactory.getInstance().getVehicle( id, equipmentName, equipmentMake, equipmentVin );
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    @Override
    public VehicleList getAll() {
        VehicleList vehicleList = new VehicleList();
        try {
            Connection connection = SQLiteDatabase.getConnection();
            PreparedStatement getAllStatement = connection.prepareStatement(
                    "SELECT equipment_id, equipment_name, equipment_make, equipment_vin " +
                            "FROM equipment;"
            );
            ResultSet resultSet = getAllStatement.executeQuery();
            while ( resultSet.next() ) {
                int equipmentID = resultSet.getInt( "equipment_id" );
                String equipmentName = resultSet.getString( "equipment_name" );
                String equipmentMake = resultSet.getString( "equipment_make" );
                String equipmentVin = resultSet.getString( "equipment_vin" );
                Vehicle vehicle = VehicleFactory.getInstance().getVehicle( equipmentID, equipmentName, equipmentMake, equipmentVin );
                vehicleList.add( vehicle );
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return vehicleList;
    }

    @Override
    public int getLastID(){
        return SQLiteDatabase.getLastID( "equipment", "equipment_id" );
    }
}

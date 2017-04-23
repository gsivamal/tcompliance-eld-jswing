package dao;

import domain.model.*;
import domain.model.Driver;
import domain.model.factory.DriverFactory;
import domain.model.factory.FuelCardFactory;
import domain.model.factory.LoadFactory;
import domain.model.factory.VehicleFactory;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class LoadDatabaseDAO implements LoadDao{

    public void add(Load item) throws SQLException {
        try {
            Connection connection = SQLiteDatabase.getConnection();
            PreparedStatement addStatement = connection.prepareStatement( "INSERT INTO load " +
                    "(load_id, start_time, end_time, truck_id, trailer1_id, trailer2_id, bl_number, fuel_card_id, driver_id) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)" );
            addStatement.setInt( 1, item.getLoadID() );
            addStatement.setString( 2, item.getStartTime().format( Clock.formatter ) );
            addStatement.setString( 3, item.getEndTime().format( Clock.formatter ) );
            if ( item.getTruck() != null ) {
                addStatement.setInt( 4, item.getTruck().getID() );
            } else {
                addStatement.setNull( 4, Types.INTEGER );
            }
            if ( item.getTrailer1() != null ) {
                addStatement.setInt( 5, item.getTrailer1().getID() );
            }else{
                addStatement.setNull( 5, Types.INTEGER );
            }
            if ( item.getTrailer2() != null ) {
                addStatement.setInt( 6, item.getTrailer2().getID() );
            }else{
                addStatement.setInt( 6,  Types.INTEGER);
            }
            addStatement.setString( 7, item.getBlNumber() );
            addStatement.setInt( 8, item.getFuelCard().getId() );
            addStatement.setInt( 9, item.getDriver().getID() );
            addStatement.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
            throw ex;
        }
    }

    public void update(Load item) throws SQLException {
        try {
            Connection connection = SQLiteDatabase.getConnection();
            PreparedStatement updateStatement = connection.prepareStatement(
                    "UPDATE load SET " +
                            "start_time = ?, end_time = ?, truck_id = ?, trailer1_id = ?, trailer2_id = ?, bl_number = ?, fuel_card_id = ?, driver_id = ? " +
                            "WHERE load_id = ?" );
            updateStatement.setInt( 9, item.getLoadID() );
            updateStatement.setString( 1, item.getStartTime().format( Clock.formatter ) );
            updateStatement.setString( 2, item.getEndTime().format( Clock.formatter ) );
            updateStatement.setInt( 3, item.getTruck().getID() );
            updateStatement.setInt( 4, item.getTrailer1().getID() );
            updateStatement.setInt( 5, item.getTrailer2().getID() );
            updateStatement.setString( 6, item.getBlNumber() );
            updateStatement.setInt( 7, item.getFuelCard().getId() );
            updateStatement.setInt( 8, item.getDriver().getID() );
            updateStatement.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public void remove(Load item) {

    }

    public Load get(int ID) throws SQLException {
        try {
            Connection connection = SQLiteDatabase.getConnection();
            PreparedStatement getStatement = connection.prepareStatement( "SELECT * FROM load WHERE load_id = ?" );
            getStatement.setInt( 1, ID );
            ResultSet resultSet = getStatement.executeQuery();
            if ( resultSet.next() ) {
                VehicleFactory vehicleFactory = VehicleFactory.getInstance();
                int loadID = resultSet.getInt( "load_id" );
                LocalDateTime startTime = LocalDateTime.parse( resultSet.getString("start_time") );
                LocalDateTime endTime = LocalDateTime.parse( resultSet.getString( "end_time" ) );
                Vehicle truck = vehicleFactory.getVehicle( resultSet.getInt( "truck_id" ) );
                Vehicle trailer1 = vehicleFactory.getVehicle( resultSet.getInt( "trailer1_id" ) );
                Vehicle trailer2 = vehicleFactory.getVehicle( resultSet.getInt( "trailer2_id" ) );
                String blNumber = resultSet.getString( "bl_number" );
                FuelCard fuelCard = FuelCardFactory.getInstance().getFuelCard( resultSet.getInt( "fuel_card_id" ) );
                Driver driver = DriverFactory.getInstance().getDriver( resultSet.getInt( "driver_id" ) );
                Load load = LoadFactory.getInstance().getLoad( loadID, startTime, endTime, truck, trailer1, trailer2, blNumber, fuelCard, driver );
                return load;
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public List<Load> getAll() throws SQLException {
        List<Load> loadList = new ArrayList<>();
        try {
            Connection connection = SQLiteDatabase.getConnection();
            PreparedStatement getStatement = connection.prepareStatement( "SELECT * FROM load" );
            ResultSet resultSet = getStatement.executeQuery();
            while ( resultSet.next() ) {
                VehicleFactory vehicleFactory = VehicleFactory.getInstance();
                int loadID = resultSet.getInt( "load_id" );
                LocalDateTime startTime = LocalDateTime.parse( resultSet.getString("start_time") );
                LocalDateTime endTime = LocalDateTime.parse( resultSet.getString( "end_time" ) );
                Vehicle truck = vehicleFactory.getVehicle( resultSet.getInt( "truck_id" ) );
                Vehicle trailer1 = vehicleFactory.getVehicle( resultSet.getInt( "trailer1_id" ) );
                Vehicle trailer2 = vehicleFactory.getVehicle( resultSet.getInt( "trailer2_id" ) );
                String blNumber = resultSet.getString( "bl_number" );
                FuelCard fuelCard = FuelCardFactory.getInstance().getFuelCard( resultSet.getInt( "fuel_card_id" ) );
                Driver driver = DriverFactory.getInstance().getDriver( resultSet.getInt( "driver_id" ) );
                Load load = LoadFactory.getInstance().getLoad( loadID, startTime, endTime, truck, trailer1, trailer2, blNumber, fuelCard, driver );
                loadList.add( load );
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return loadList;
    }

    @Override
    public int getLastID() throws SQLException {
        return SQLiteDatabase.getLastID( "load", "load_id" );
    }

    @Override
    public Load getLatestOpenLoad() throws SQLException {
        Connection connection = SQLiteDatabase.getConnection();
        PreparedStatement getStatement = connection.prepareStatement( "SELECT * FROM load " +
                "" +
                "ORDER BY load_id DESC " +
                "LIMIT 1" );
        ResultSet resultSet = getStatement.executeQuery();
        if ( resultSet.next() ) {
            VehicleFactory vehicleFactory = VehicleFactory.getInstance();
            int loadID = resultSet.getInt( "load_id" );
            LocalDateTime startTime = LocalDateTime.parse( resultSet.getString("start_time"), Clock.formatter );
            LocalDateTime endTime = LocalDateTime.parse( resultSet.getString( "end_time" ), Clock.formatter );
            Vehicle truck = vehicleFactory.getVehicle( resultSet.getInt( "truck_id" ) );
            Vehicle trailer1 = vehicleFactory.getVehicle( resultSet.getInt( "trailer1_id" ) );
            Vehicle trailer2 = vehicleFactory.getVehicle( resultSet.getInt( "trailer2_id" ) );
            String blNumber = resultSet.getString( "bl_number" );
            FuelCard fuelCard = FuelCardFactory.getInstance().getFuelCard( resultSet.getInt( "fuel_card_id" ) );
            Driver driver = DriverFactory.getInstance().getDriver( resultSet.getInt( "driver_id" ) );
            Load load = LoadFactory.getInstance().getLoad( loadID, startTime, endTime, truck, trailer1, trailer2, blNumber, fuelCard, driver );
            return load;
        }
        return null;
    }


}

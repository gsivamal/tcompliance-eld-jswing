package dao;

import model.Fuel;
import model.Service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class FuelDatabaseDAO implements FuelDao{


    public void add(Service service) {
        try {
            Connection connection = DbUtil.getConnection();
            PreparedStatement addStatement = connection.prepareStatement( "INSERT INTO FUEL" +
                    "(service_id, trailer1_status, trailer2_status, fuel_card_number) VALUES " +
                    "(?, ?, ?, ?)" );
            addStatement.setInt( 1, service.getServiceID() );
            addStatement.setString( 2, service.getFuel().getTrailer1Status() );
            addStatement.setString( 3, service.getFuel().getTrailer2Status() );
            addStatement.setInt( 4, service.getFuel().getFuelCardNumber() );
            addStatement.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public void update(Service service, Fuel item) {
        try {
            Connection connection = DbUtil.getConnection();
            PreparedStatement updateStatement = connection.prepareStatement( "UPDATE fuel SET " +
                    "trailer1_status = ?, trailer2_status = ?, fuel_card_number = ?" +
                    "WHERE service_id = ?" );
            updateStatement.setString( 1, item.getTrailer1Status() );
            updateStatement.setString( 2, item.getTrailer2Status() );
            updateStatement.setInt( 3, item.getFuelCardNumber() );
            updateStatement.setInt( 4, service.getServiceID() );
            updateStatement.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public void remove(Fuel item) {

    }

    public Fuel get(int serviceID) {
        try {
            Connection connection = DbUtil.getConnection();
            PreparedStatement getStatement = connection.prepareStatement( "SELECT * FROM fuel WHERE service_id = ?" );
            getStatement.setInt( 1, serviceID );
            ResultSet resultSet = getStatement.executeQuery();
            if ( resultSet.next() ) {
                String trailer1Status = resultSet.getString( "trailer1_status" );
                String trailer2Status = resultSet.getString( "trailer2_status" );
                int fuelCardNumber = resultSet.getInt( "fuel_card_number" );
                return new Fuel( fuelCardNumber, trailer1Status, trailer2Status );
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public ArrayList<Fuel> getAll() {
        throw new UnsupportedOperationException();
    }

}

package dao;

import model.Load;
import model.Service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;

public class LoadDatabaseDAO implements LoadDao{


    public void add(Service service, Load item) {
        try {
            Connection connection = DbUtil.getConnection();
            PreparedStatement addStatement = connection.prepareStatement( "INSERT INTO load" +
                    "(load_number, service_id, start_time, end_time) VALUES " +
                    "(?, ?, ?, ?)" );
            addStatement.setInt( 1, item.getLoadNumber() );
            addStatement.setInt( 2, service.getServiceID() );
            addStatement.setLong( 3, item.getStartTime().toEpochSecond( ZoneOffset.UTC ) );
            addStatement.setLong( 4, item.getEndTime().toEpochSecond( ZoneOffset.UTC ) );
            addStatement.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public void update(Service service, Load item) {
        try {
            Connection connection = DbUtil.getConnection();
            PreparedStatement updateStatement = connection.prepareStatement( "UPDATE load SET " +
                    "load_number = ?, start_time = ?, end_time = ?" +
                    "WHERE service_id = ?" );
            updateStatement.setInt( 1, item.getLoadNumber() );
            updateStatement.setLong( 2, item.getStartTime().toEpochSecond( ZoneOffset.UTC ) );
            updateStatement.setLong( 3, item.getEndTime().toEpochSecond( ZoneOffset.UTC ) );
            updateStatement.setInt( 4, service.getServiceID() );
            updateStatement.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public void remove(Load item) {

    }

    public Load get(int serviceID) {
        try {
            Connection connection = DbUtil.getConnection();
            PreparedStatement getStatement = connection.prepareStatement( "SELECT * FROM load WHERE service_id = ?" );
            getStatement.setInt( 1, serviceID );
            ResultSet resultSet = getStatement.executeQuery();
            if ( resultSet.next() ) {
                int loadNumber = resultSet.getInt( "load_number" );
                LocalDateTime startTime = LocalDateTime.parse( resultSet.getString("start_time") );
                LocalDateTime endTime = LocalDateTime.parse( resultSet.getString( "end_time" ) );
                return new Load( loadNumber, startTime, endTime );
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public ArrayList<Load> getAll() {
        throw new UnsupportedOperationException();
    }
}

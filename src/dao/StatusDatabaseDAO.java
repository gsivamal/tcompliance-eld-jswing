package dao;

import model.Service;
import model.Status;
import model.StatusHistory;
import model.factory.StatusFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

public class StatusDatabaseDAO implements StatusDao {


    public void add(Service service, Status item) {
        try {
            Connection connection = DbUtil.getConnection();
            PreparedStatement addStatement = connection.prepareStatement( "INSERT INTO status " +
                    "(status_id, service_id, status_value, start_time, end_time) " +
                    "VALUES (?, ?, ?, ?, ?)" );
            addStatement.setInt( 1, item.getStatusID() );
            addStatement.setInt( 2, service.getServiceID() );
            addStatement.setString( 3, item.getStatusValue().toString() );
            addStatement.setLong( 4, item.getStartTime().toEpochSecond( ZoneOffset.UTC ) );
            addStatement.setLong( 5, item.getEndTime().toEpochSecond( ZoneOffset.UTC ) );
            addStatement.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public void update(Service service, Status item) {
        try {
            Connection connection = DbUtil.getConnection();
            PreparedStatement updateStatement = connection.prepareStatement( "UPDATE status SET " +
                    "service_id = ?, status_value = ?, start_time = ?, end_time = ? " +
                    "WHERE status_id = ?" );
            updateStatement.setInt( 1, service.getServiceID() );
            updateStatement.setString( 2, item.getStatusValue().toString() );
            updateStatement.setLong( 3, item.getStartTime().toEpochSecond( ZoneOffset.UTC ) );
            updateStatement.setLong( 4, item.getEndTime().toEpochSecond( ZoneOffset.UTC ) );
            updateStatement.setInt( 5, item.getStatusID() );
            updateStatement.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public void remove(Status item) {

    }

    @Override
    public StatusHistory getAllService(int serviceID) {
        StatusHistory statuses = new StatusHistory();
        try {
            Connection connection = DbUtil.getConnection();
            PreparedStatement retrieveAllServiceStatement = connection.prepareStatement( "SELECT * FROM status WHERE service_id = ?" );
            retrieveAllServiceStatement.setInt( 1, serviceID );
            ResultSet resultSet = retrieveAllServiceStatement.executeQuery();
            while ( resultSet.next() ) {
                int statusID = resultSet.getInt( "status_id" );
                Status.StatusValue statusValue = Status.StatusValue.valueOf( resultSet.getString( "status_value" ) );
                LocalDateTime startTime = LocalDateTime.ofEpochSecond( Long.parseLong( resultSet.getString( "start_time" ) ), 0, ZoneOffset.UTC );
                LocalDateTime endTime = LocalDateTime.ofEpochSecond( Long.parseLong( resultSet.getString( "end_time" ) ), 0, ZoneOffset.UTC );
                statuses.addStatus( StatusFactory.getInstance().createStatus( statusID, statusValue, startTime, endTime ) );
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return statuses;
    }

    public StatusHistory getAll() {
        return null;
    }

    public int getCount(){
        try {
            Connection connection = DbUtil.getConnection();
            PreparedStatement getCountStatement = connection.prepareStatement( "SELECT COUNT(*) FROM status" );
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

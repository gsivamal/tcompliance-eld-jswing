package dao;

import model.LogbookStatus;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class LogbookStatusDatabaseDAO implements LogbookStatusDao {

    private static final LogbookStatusDatabaseDAO instance = new LogbookStatusDatabaseDAO();
    private LogbookStatusDatabaseDAO(){}

    public static LogbookStatusDatabaseDAO getInstance(){
        return instance;
    }

    @Override
    public void add(LogbookStatus status) {
        try {
            Connection connection = DbUtil.getConnection();
            PreparedStatement addStatement = connection.prepareStatement(
                    "INSERT INTO logbook_status " +
                            "(logbook_status_id, logbook_status_value) " +
                            "VALUES (?, ?);"
            );
            addStatement.setInt( 1, status.getID() );
            addStatement.setString( 2, status.toString() );
            addStatement.executeUpdate();
            addStatement.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void remove(LogbookStatus status) {
        try {
            Connection connection = DbUtil.getConnection();
            PreparedStatement removeStatement = connection.prepareStatement(
                    "DELETE FROM logbook_status " +
                            "WHERE logbook_status_id = ?;"
            );
            removeStatement.setInt( 1, status.getID() );
            removeStatement.executeUpdate();
            removeStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void removeAll() {
        try {
            Connection connection = DbUtil.getConnection();
            PreparedStatement removeAllStatement = connection.prepareStatement(
                    "DELETE FROM logbook_status;"
            );
            removeAllStatement.executeUpdate();
            removeAllStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

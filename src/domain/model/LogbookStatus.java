package domain.model;

import dao.SQLiteDatabase;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public enum LogbookStatus {
    WAITING( 1, "#4f81bd" ),
    APPROVED( 2, "#e46c0a" ),
    DECLINED( 3, "#c00000" );

    private String colorHexCode;
    private int ID;

    LogbookStatus(int ID, String colorHexCode) {
        this.ID = ID;
        this.colorHexCode = colorHexCode;
    }

    public String getColorHexCode(){
        return this.colorHexCode;
    }

    public int getID() {
        return ID;
    }

    public static LogbookStatus getByID(int ID) {
        LogbookStatus [] statuses = LogbookStatus.values();
        for (LogbookStatus status : statuses) {
            if ( status.getID() == ID ) {
                return status;
            }
        }
        return null;
    }

    public static void insertValuesDatabase(){
        try {
            Connection connection = SQLiteDatabase.getConnection();
            PreparedStatement insertStatement = connection.prepareStatement(
                    "INSERT OR IGNORE INTO logbook_status " +
                            "(logbook_status_id, logbook_status_value) " +
                            "VALUES (?, ?);"
            );
            LogbookStatus [] statuses = LogbookStatus.values();
            for (LogbookStatus status : statuses) {
                insertStatement.setInt( 1, status.getID() );
                insertStatement.setString( 2, status.toString() );
                insertStatement.executeUpdate();
            }
            insertStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

package model;

import dao.DbUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public enum DutyStatus {
    OFF( 1, "#c00000" ),
    SB( 2, "#4f81bd" ),
    DRV( 3, "#4f6228" ),
    ON( 4, "#e46c0a" );

    private String colorHexCode;
    private int ID;

    DutyStatus(int ID, String colorHexCode) {
        this.ID = ID;
        this.colorHexCode = colorHexCode;
    }

    public String getColorHexCode(){
        return this.colorHexCode;
    }

    public int getID() {
        return ID;
    }

    public static DutyStatus getByID(int ID) {
        DutyStatus [] statuses = DutyStatus.values();
        for (DutyStatus status : statuses) {
            if ( status.getID() == ID ) {
                return status;
            }
        }
        return null;
    }

    public static void insertValuesDatabase(){
        try {
            Connection connection = DbUtil.getConnection();
            PreparedStatement insertStatement = connection.prepareStatement(
                    "INSERT OR IGNORE INTO duty_status " +
                            "(duty_status_id, duty_status_value) " +
                            "VALUES (?, ?);"
            );
            DutyStatus [] statuses = DutyStatus.values();
            for (DutyStatus status : statuses) {
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

package dao;

import domain.model.EldDevice;
import domain.model.factory.EldDeviceFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EldDeviceSQLiteDB implements EldDeviceDao{

    @Override
    public void add(EldDevice eldDevice) throws DaoException {
        try {
            Connection connection = SQLiteDatabase.getConnection();
            PreparedStatement addStatement = connection.prepareStatement(
                    "INSERT INTO eld_device " +
                            "(manufacturer, version, id) " +
                            "VALUES (?, ?, ?)"
            );
            addStatement.setString( 1, eldDevice.getManufacturer() );
            addStatement.setString( 2, eldDevice.getVersion() );
            addStatement.setInt( 3, eldDevice.getId() );
            addStatement.execute();
            addStatement.close();
        } catch (SQLException e) {
            throw new DaoException( DaoExceptionType.SQL, e.getMessage() );
        }
    }

    @Override
    public void remove(EldDevice eldDevice) throws DaoException {
        try {
            Connection connection = SQLiteDatabase.getConnection();
            PreparedStatement removeStatement = connection.prepareStatement(
                    "DELETE FROM eld_device " +
                            "WHERE id = ?"
            );
            removeStatement.setInt( 1, eldDevice.getId() );
            removeStatement.execute();
            removeStatement.close();
        } catch (SQLException e) {
            throw new DaoException( DaoExceptionType.SQL, e.getMessage() );
        }
    }

    @Override
    public EldDevice get(int id) throws DaoException {
        return getEldDevices( id ).get( 0 );
    }

    @Override
    public List<EldDevice> getAll() throws DaoException {
        return getEldDevices( null );
    }

    private List<EldDevice> getEldDevices(Integer id) {
        List<EldDevice> list = new ArrayList<>();
        try {
            Connection connection = SQLiteDatabase.getConnection();
            PreparedStatement getStatement;
            if ( id == null ) {
                getStatement = connection.prepareStatement(
                        "SELECT id, manufacturer, version " +
                                "FROM eld_device"
                );
            }else {
                getStatement = connection.prepareStatement(
                        "SELECT id, manufacturer, version " +
                                "FROM eld_device " +
                                "WHERE id = ?"
                );
                getStatement.setInt( 1, id );
            }
            ResultSet resultSet = getStatement.executeQuery();
            while ( resultSet.next() ) {
                int resultID = resultSet.getInt( "id" );
                String manufacturer = resultSet.getString( "manufacturer" );
                String version = resultSet.getString( "version" );
                EldDevice eldDevice = EldDeviceFactory.getInstance().
                        getEldDevice( resultID, manufacturer, version );
                list.add( eldDevice );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
}

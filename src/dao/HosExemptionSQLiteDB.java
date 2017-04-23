package dao;

import domain.model.HosExemption;
import domain.model.factory.HosExemptionFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class HosExemptionSQLiteDB implements HosExemptionDao{

    @Override
    public void add(HosExemption hosExemption) throws DaoException {
        try {
            Connection connection = SQLiteDatabase.getConnection();
            PreparedStatement addStatement = connection.prepareStatement(
                    "INSERT INTO hos_exemptions " +
                            "(exemption, status, id) " +
                            "VALUES (?, ?, ?)"
            );
            addStatement.setString( 1, hosExemption.getExemption() );
            addStatement.setInt( 2, hosExemption.getStatus() ? 1 : 0 );
            addStatement.setInt( 3, hosExemption.getId() );
            addStatement.execute();
            addStatement.close();
        } catch (SQLException e) {
            throw new DaoException( DaoExceptionType.SQL, e.getMessage() );
        }
    }

    @Override
    public void update(HosExemption hosExemption) throws DaoException {
        try {
            Connection connection = SQLiteDatabase.getConnection();
            PreparedStatement updateStatement = connection.prepareStatement(
                    "UPDATE hos_exemptions " +
                            "SET exemption = ?, status = ? " +
                            "WHERE id = ?"
            );
            updateStatement.setString( 1, hosExemption.getExemption() );
            updateStatement.setInt( 2, hosExemption.getStatus() ? 1 : 0 );
            updateStatement.setInt( 3, hosExemption.getId() );
            updateStatement.execute();
            updateStatement.close();
        } catch (SQLException e) {
            throw new DaoException( DaoExceptionType.SQL, e.getMessage() );
        }
    }

    @Override
    public void remove(HosExemption hosExemption) throws DaoException {
        try {
            Connection connection = SQLiteDatabase.getConnection();
            PreparedStatement removeStatement = connection.prepareStatement(
                    "DELETE FROM hos_exemptions " +
                            "WHERE id = ?"
            );
            removeStatement.setInt( 1, hosExemption.getId() );
            removeStatement.execute();
            removeStatement.close();
        } catch (SQLException e) {
            throw new DaoException( DaoExceptionType.SQL, e.getMessage() );
        }
    }

    @Override
    public HosExemption get(int id) throws DaoException {
        return getHosExemptions( id ).get( 0 );
    }

    @Override
    public List<HosExemption> getAll() throws DaoException {
        return getHosExemptions( null );
    }

    private List<HosExemption> getHosExemptions(Integer id) throws DaoException {
        List<HosExemption> list = new ArrayList<>();
        try {
            Connection connection = SQLiteDatabase.getConnection();
            PreparedStatement getStatement;
            if ( id == null ) {
                getStatement = connection.prepareStatement(
                        "SELECT id, exemption, status " +
                                "FROM hos_exemptions"
                );
            }else{
                getStatement = connection.prepareStatement(
                        "SELECT id, exemption, status " +
                                "FROM hos_exemptions " +
                                "WHERE id = ?"
                );
                getStatement.setInt( 1, id );
            }
            ResultSet resultSet = getStatement.executeQuery();
            while ( resultSet.next() ) {
                int resultID = resultSet.getInt( "id" );
                String exemption = resultSet.getString( "exemption" );
                boolean status = resultSet.getInt( "status" ) == 1;
                HosExemption hosExemption = HosExemptionFactory.getInstance().
                        getHosExemption( resultID, exemption, status );
                list.add( hosExemption );
            }
        } catch (SQLException e) {
            throw new DaoException( DaoExceptionType.SQL, e.getMessage() );
        }
        return list;
    }
}
